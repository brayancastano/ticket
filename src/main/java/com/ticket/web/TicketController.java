package com.ticket.web;

import com.ticket.entity.DetailTicket;
import com.ticket.repository.TicketRepository;
import com.ticket.entity.Ticket;
import com.ticket.entity.dto.DetailTicketDTO;
import com.ticket.entity.dto.TicketDTO;
import com.ticket.repository.DetailTicketRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DetailTicketRepository detailTicketRepository;

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDTO>> getTickets() {

        List<TicketDTO> ticketDTO = ticketRepository.findAll().stream().map(t -> {
            TicketDTO temp = new TicketDTO();
            temp.setId(t.getId());
            temp.setCreationDate(t.getCreationDate());
            temp.setTotalAmount(t.getTotalAmount());
            temp.setDetailTicket(detailTicketRepository.findByIdTicket(t.getId()).stream().map(d -> {
                DetailTicketDTO tempdetailTicket = new DetailTicketDTO();
                tempdetailTicket.setId(d.getId());
                tempdetailTicket.setDescription(d.getDescription());
                tempdetailTicket.setAmount(d.getAmount());
                return tempdetailTicket;
            }).collect(Collectors.toList()));
            return temp;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ticketDTO);
    }

    @DeleteMapping("/tickets/{idTicket}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer idTicket) {
        if (ticketRepository.existsById(idTicket)) {
            detailTicketRepository.deleteAll(detailTicketRepository.findByIdTicket(idTicket));
            detailTicketRepository.flush();
            ticketRepository.deleteById(idTicket);
            ticketRepository.flush();
            return ResponseEntity
                    .noContent().header("delete", "Ticket con ID " + idTicket)
                    .build();

        } else {
            return ResponseEntity
                    .noContent().header("delete", "Ticket con ID " + idTicket + " no existe")
                    .build();
        }
    }

    @PutMapping("/tickets")
    public ResponseEntity<TicketDTO> putTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDTO.getId());
        ticket.setTotalAmount(ticketDTO.getTotalAmount());
        ticket.setCreationDate(ticketDTO.getCreationDate());
        ticketRepository.save(ticket);
        List<DetailTicket> detailTicket = new ArrayList<>();
        detailTicket = ticketDTO.getDetailTicket().stream().map(d -> {
            DetailTicket tempdetailTicket = new DetailTicket();
            tempdetailTicket.setId(d.getId());
            tempdetailTicket.setDescription(d.getDescription());
            tempdetailTicket.setAmount(d.getAmount());
            tempdetailTicket.setIdTicket(ticketDTO.getId());
            return tempdetailTicket;
        }).collect(Collectors.toList());
        detailTicketRepository.saveAll(detailTicket);
        return ResponseEntity.status(HttpStatus.OK).body(ticketDTO);
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketDTO> postTicket(@RequestBody TicketDTO ticketDTO) {
        if (ticketDTO.getId() != null) {
            Ticket ticket = new Ticket();
            ticket.setTotalAmount(ticketDTO.getTotalAmount());
            ticket.setCreationDate(ticketDTO.getCreationDate());
            ticket = ticketRepository.save(ticket);
            ticketDTO.setId(ticket.getId());
            List<DetailTicket> detailTicket = new ArrayList<>();
            detailTicket = ticketDTO.getDetailTicket().stream().map(d -> {
                DetailTicket tempdetailTicket = new DetailTicket();
                tempdetailTicket.setId(d.getId());
                tempdetailTicket.setDescription(d.getDescription());
                tempdetailTicket.setAmount(d.getAmount());
                tempdetailTicket.setIdTicket(ticketDTO.getId());
                return tempdetailTicket;
            }).collect(Collectors.toList());

            List<DetailTicketDTO> detailTicketDTO = detailTicketRepository.saveAll(detailTicket).stream().map(d -> {
                DetailTicketDTO tempdetailTicket = new DetailTicketDTO();
                tempdetailTicket.setId(d.getId());
                tempdetailTicket.setDescription(d.getDescription());
                tempdetailTicket.setAmount(d.getAmount());
                return tempdetailTicket;
            }).collect(Collectors.toList());
            ticketDTO.setDetailTicket(detailTicketDTO);

            return ResponseEntity.status(HttpStatus.OK).body(ticketDTO);
        } else {
            return ResponseEntity.internalServerError().header("create", "Error creando ticket el id no se debe enviar").body(null);
        }

    }

    @GetMapping("/tickets/{idTicket}")
    public ResponseEntity<TicketDTO> getByIdTicket(@PathVariable Integer idTicket) {
        Ticket ticket = new Ticket();
        ticket = ticketRepository.findById(idTicket).get();
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setCreationDate(ticket.getCreationDate());
        ticketDTO.setTotalAmount(ticket.getTotalAmount());

        ticketDTO.setDetailTicket(detailTicketRepository.findByIdTicket(idTicket).stream().map(d -> {
            DetailTicketDTO tempdetailTicket = new DetailTicketDTO();
            tempdetailTicket.setId(d.getId());
            tempdetailTicket.setDescription(d.getDescription());
            tempdetailTicket.setAmount(d.getAmount());
            return tempdetailTicket;
        }).collect(Collectors.toList()));

        return ResponseEntity.ok(ticketDTO);
    }

    @GetMapping("/tickets/filter")
    public ResponseEntity<List<TicketDTO>> getByStartDateAndEndDate(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<TicketDTO> ticketDTO = ticketRepository.findByStartDateAndEndDate(startDate, endDate).stream().map(t -> {
            TicketDTO temp = new TicketDTO();
            temp.setId(t.getId());
            temp.setCreationDate(t.getCreationDate());
            temp.setTotalAmount(t.getTotalAmount());
            temp.setDetailTicket(detailTicketRepository.findByIdTicket(t.getId()).stream().map(d -> {
                DetailTicketDTO tempdetailTicket = new DetailTicketDTO();
                tempdetailTicket.setId(d.getId());
                tempdetailTicket.setDescription(d.getDescription());
                tempdetailTicket.setAmount(d.getAmount());
                return tempdetailTicket;
            }).collect(Collectors.toList()));
            return temp;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(ticketDTO);
    }
}
