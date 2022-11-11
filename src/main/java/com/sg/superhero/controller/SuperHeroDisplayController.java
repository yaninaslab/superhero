package com.sg.superhero.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.superhero.dto.SightingDTO;
import com.sg.superhero.dto.SuperHeroDTO;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.SuperHero;
import com.sg.superhero.entities.SuperHeroSighting;
import com.sg.superhero.entities.SuperPower;
import com.sg.superhero.repositories.LocationRepository;
import com.sg.superhero.repositories.OrganizationRepository;
import com.sg.superhero.repositories.SuperHeroRepository;
import com.sg.superhero.repositories.SuperHeroSightingRepository;
import com.sg.superhero.repositories.SuperPowerRepository;


@Controller
public class SuperHeroDisplayController {
    @Autowired
    OrganizationRepository organizationRepo;

    @Autowired
    LocationRepository locationRepo;
    
    @Autowired
    SuperHeroRepository superHeroRepo;
    
    @Autowired
    SuperHeroSightingRepository superHeroSightingRepository;
    
    @Autowired
    SuperPowerRepository superPowerRepository;
    
    @Autowired
    ObjectMapper objectMapper;
   
    DateTimeFormatter pattern=DateTimeFormatter.ofPattern("MM-dd-yyyy");
    
    List<String> orgSelected;
    

    @GetMapping("/")
    public String displayHomePage(Model model) {
        List<SuperHeroSighting> superHeroSighting = superHeroSightingRepository.findAll();
        model.addAttribute("superHeroSighting", superHeroSighting);
        return "homepage";
    }
    

    @GetMapping("locations")
    public String displayLocationPage(Model model) {
        List<Location> locations = locationRepo.findAll();
        model.addAttribute("locations", locations);
        return "locations";
    }
    

    @GetMapping("organizations")
    public String displayOrganizationPage(Model model) {
        List<Organization> orgs = organizationRepo.findAll();
        List<SuperHero> superHero = superHeroRepo.findAll();

        model.addAttribute("organizations", orgs);
        model.addAttribute("superHero", superHero);
        return "organizations";
    }
    

    @GetMapping("superheros")
    public String displaySuperHeroPage(Model model) {
        List<Organization> orgs = organizationRepo.findAll();
        List<SuperPower> superPowers = superPowerRepository.findAll();
        
        List<SuperHero> superHero = superHeroRepo.findAll();
        model.addAttribute("organizations", orgs);
        model.addAttribute("superpowers", superPowers);
        model.addAttribute("superHeroObj",new SuperHeroDTO());
        model.addAttribute("superheros",superHero);
        
        return "superheros";
    }
    
    
    @GetMapping("sightings")
    public String displaySightingsPage(Model model) {
        List<Location> locations = locationRepo.findAll();        
        List<SuperHero> superHero = superHeroRepo.findAll();
        List<SuperHeroSighting> superHeroSighting = superHeroSightingRepository.findAll();
        
        model.addAttribute("superHeroSighting", superHeroSighting);
        model.addAttribute("locations", locations);
        model.addAttribute("superHero", superHero);
        model.addAttribute("sightingObj",new SightingDTO());

        return "sightings";
    }
    
    @GetMapping("superpowers")
    public String displaySuperPowers(Model model) {
        List<SuperPower> superpowers = superPowerRepository.findAll();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }
  
}
