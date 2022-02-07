package com.ticket.entity.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class DetailTicketDTO implements Serializable{
 
    private Integer id;
    private String description;
    private Integer amount;   
}
