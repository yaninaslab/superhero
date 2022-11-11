package com.sg.superhero.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
@Entity
@Table(name = "ORGANIZATIONDETAILS")
public class Organization {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(nullable = false, unique=true)
    @NotBlank(message = "Name must not be empty.")
    private String name;
    
    @Column
    @NotBlank(message = "Description must not be empty.")
    private String description;
    
    @Column
    private String address;
    
    @Column
    private String contact;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "organizations")
    private List<SuperHero> superHeros;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<SuperHero> getSuperHeros() {
        return superHeros;
    }

    public void setSuperHeros(List<SuperHero> superHeros) {
        this.superHeros = superHeros;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
