/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ticket.repository;

import com.ticket.entity.DetailTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailTicketRepository extends JpaRepository<DetailTicket, Integer> {

    List<DetailTicket> findByIdTicket(int ciudadID);
}
