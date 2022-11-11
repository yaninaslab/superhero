package com.sg.superhero.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "SUPERHEROSIGHTINGS")
public class SuperHeroSighting {
    
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(nullable = false)
    private LocalDate sightingdate;
 
    @ManyToOne
    @JoinColumn(name = "superheroid", nullable = false)
    private SuperHero superHero;
    
    @ManyToOne
    @JoinColumn(name = "locationid", nullable = false)
    private Location location;

    public LocalDate getSightingdate() {
        return sightingdate;
    }

    public void setSightingdate(LocalDate sightingdate) {
        this.sightingdate = sightingdate;
    }

    public SuperHero getSuperHero() {
        return superHero;
    }

    public void setSuperHero(SuperHero superHero) {
        this.superHero = superHero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "SuperHeroSighting [sightingdate=" + sightingdate + ", superHero=" + superHero + ", location=" + location
                + "]";
    }
    
    
}
