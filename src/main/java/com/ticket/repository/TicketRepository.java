package com.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.entity.Ticket;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(value = "SELECT * FROM Ticket u WHERE u.creation_date >= :startDate and u.creation_date <= :endDate", nativeQuery = true)
    List<Ticket> findByStartDateAndEndDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
