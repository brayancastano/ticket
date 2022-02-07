package com.ticket.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class TicketDTO implements Serializable{
    
    private Integer id;
    private Date creationDate;
    private Integer totalAmount;
    private List<DetailTicketDTO> detailTicket;


}
