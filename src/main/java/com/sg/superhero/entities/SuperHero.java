package com.sg.superhero.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "SUPERHERODETAILS")
public class SuperHero {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(nullable = false, unique=true)
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message="Name must be less than 30 characters.")
    private String name;
    
    @Column
    @NotBlank(message = "Name must not be empty.")
    private String description;
    
    @Column(nullable = false)
    private boolean isvillan = false;
    
    @ManyToMany
    @JoinTable(name = "ORGHERODETAILS",
            joinColumns = {@JoinColumn(name = "superheroid")},
            inverseJoinColumns = {@JoinColumn(name = "organizationid")})
    private List<Organization> organizations;

    @ManyToOne
    @JoinColumn(name = "superpowerid", nullable = false)
    private SuperPower superpower;

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

    public boolean isIsvillan() {
        return isvillan;
    }


    public void setIsvillan(boolean isvillan) {
        this.isvillan = isvillan;
    }

    

    public List<Organization> getOrganizations() {
        return organizations;
    }


    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    

    public SuperPower getSuperpower() {
        return superpower;
    }


    public void setSuperpower(SuperPower superpower) {
        this.superpower = superpower;
    }


    @Override
    public String toString() {
        return "SuperHero [id=" + id + ", name=" + name + ", description=" + description 
                + ", isvillan=" + isvillan + "]";
    }
    
    
    
}
