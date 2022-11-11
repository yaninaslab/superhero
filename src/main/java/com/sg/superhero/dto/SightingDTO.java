package com.sg.superhero.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SightingDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
}
