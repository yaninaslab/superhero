package com.sg.superhero.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "LOCATIONDETAILS")
public class Location {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(nullable = false, unique=true)
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message="Name must be less than 30 characters.")
    private String name;
    
    @Column
    @NotBlank(message = "Description must not be empty.")
    private String description;
    
    @Column
    private String address;
    
    @Column
    private String latitude;

    @Column
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    
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


    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", description=" + description + ", address=" + address + ", latitude=" + latitude + ", longitute=" + longitude + '}';
    }


}
