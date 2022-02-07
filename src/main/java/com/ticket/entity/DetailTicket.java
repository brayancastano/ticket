package com.ticket.entity;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detail_ticket")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetailTicket implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id" )
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "description", nullable = false )
    private String description;

    @Basic(optional = false)
    @Column(name = "amount", nullable = false)    
    private Integer amount;

    @Basic(optional = false)
    @Column(name = "id_ticket")
    private Integer idTicket;

}
