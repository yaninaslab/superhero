/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.superhero.repositories;

import com.sg.superhero.entities.Location;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author yanina
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;
    
    Location location;
    
    public LocationRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        Random rg = new Random();
        location = new Location();
        location.setName("NewLocation "+rg.nextInt());
        location.setDescription("NewDescrption");
        location.setAddress("NewAddress");
        location.setLatitude("123");
        location.setLongitude("234");
    }

    @After
    public void tearDown() {
        
    }
    
    @Test
    public void testLocationCreate() {
        location = locationRepository.save(location);
        
        System.out.println("location "+location.getId());
        
        assertNotNull(location.getId());
        
        //Clean Up
//        locationRepository.delete(location);
    }
    
    @Test
    public void testLocationGet() {
        location = locationRepository.save(location);
        
        Location addedLocation = locationRepository.findById(location.getId()).orElse(null);
        
        assertEquals(location.getId(), addedLocation.getId());
        
        //Clean Up
//        locationRepository.delete(location);
    }
    
    @Test
    public void testGetAllLocation() {

        location = locationRepository.save(location);
        
        List<Location> locations = locationRepository.findAll();
        
        assertNotNull(locations);
        assertTrue(locations.size() > 0);
        
        //Clean Up
//        locationRepository.delete(location);
    }
    
    @Test
    public void testDeleteLocation() {

        location = locationRepository.save(location);
        
        Location addedLocation = locationRepository.findById(location.getId()).orElse(null);
        
        assertNotNull(addedLocation);
        
        //Clean Up
        locationRepository.delete(location);
        
        addedLocation = locationRepository.findById(location.getId()).orElse(null);
        
        assertNull(addedLocation); 

    }
    
}
