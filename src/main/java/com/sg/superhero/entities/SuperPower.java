package com.sg.superhero.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "SUPERPOWERS")
public class SuperPower {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(nullable = false, unique=true)
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message="Name must be less than 30 characters.")
    private String name;
    
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "SuperPowers [id=" + id + ", name=" + name + "]";
    }
    
    
}
