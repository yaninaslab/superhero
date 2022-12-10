/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.sg.superhero.repositories;

import com.sg.superhero.entities.Organization;
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
public class OrganizationRepositoryTest {

    @Autowired
    OrganizationRepository organizationRepository;
    
    Organization organization;
    
    public OrganizationRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        Random rg = new Random();
        organization = new Organization();
        organization.setName("NewLocation "+rg.nextInt());
        organization.setDescription("NewDescrption");
        organization.setAddress("NewAddress");
    }

    @After
    public void tearDown() {
      //Clean Up
        organizationRepository.delete(organization);
    }
    
    @Test
    public void testLocationCreate() {
        organization = organizationRepository.save(organization);        
        
        assertNotNull(organization.getId());
        
        //Clean Up
        organizationRepository.delete(organization);
    }
    
    @Test
    public void testLocationGet() {
        organization = organizationRepository.save(organization);
        
        Organization addedorganization = organizationRepository.findById(organization.getId()).orElse(null);
        
        assertEquals(organization.getId(), addedorganization.getId());
        
        //Clean Up
        organizationRepository.delete(organization);
    }
    
    @Test
    public void testGetAllLocation() {

        organization = organizationRepository.save(organization);
        
        List<Organization> organizations = organizationRepository.findAll();
        
        assertNotNull(organizations);
        assertTrue(organizations.size() > 0);
        
        //Clean Up
        organizationRepository.delete(organization);
    }
    
    @Test
    public void testDeleteLocation() {

        organization = organizationRepository.save(organization);
        
        Organization addedLocation = organizationRepository.findById(organization.getId()).orElse(null);
        
        assertNotNull(addedLocation);
        
        //Clean Up
        organizationRepository.delete(organization);
        
        addedLocation = organizationRepository.findById(organization.getId()).orElse(null);
        
        assertNull(addedLocation); 

    }
}