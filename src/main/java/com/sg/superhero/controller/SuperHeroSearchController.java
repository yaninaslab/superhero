package com.sg.superhero.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public class SuperHeroSearchController {
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
    

    @PostMapping("/filterOrganization")
    public String filterOrganization(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        
        String superHeroSelected = request.getParameter("superHeroSelected");
        List<SuperHero> superHeros = superHeroRepo.findAll();
        List<Organization> orgs = null;
        SuperHero superHero = null;
        if(null != superHeroSelected && !StringUtils.isEmpty(superHeroSelected)) {
            superHero = superHeroRepo.findById(Integer.valueOf(superHeroSelected)).orElse(null);
            orgs = superHero.getOrganizations();
            model.addAttribute("searchmessage", "Organizations belonging to SuperHero "+superHero.getName());
        }else {
            orgs = organizationRepo.findAll();
            model.addAttribute("searchmessage", "ALL Organizations");

        }
        
        model.addAttribute("organizations", orgs);
        model.addAttribute("superHero", superHeros); 
        return "organizations";
        
    }
    

    @PostMapping("/filterSuperHeros")
    public String filterSuperHeros(Model model, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        
        String organizationSelected = request.getParameter("organizationSelected");
        List<SuperHero> superHeros = null;
        List<Organization> orgs = organizationRepo.findAll();
        List<SuperPower> superPowers = superPowerRepository.findAll();
        
        if(null != organizationSelected && !StringUtils.isEmpty(organizationSelected)) {
            Organization org = organizationRepo.findById(Integer.valueOf(organizationSelected)).orElse(null);
            model.addAttribute("superheros",org.getSuperHeros());
            
            model.addAttribute("searchmessage", "SuperHeros belonging to Organization "+org.getName());
        }else {
            superHeros = superHeroRepo.findAll();
            model.addAttribute("searchmessage", "ALL SuperHeros");
            model.addAttribute("superheros",superHeros);
        }
                
        model.addAttribute("organizations", orgs);
        model.addAttribute("superpowers", superPowers);
        model.addAttribute("superHeroObj",new SuperHeroDTO());
        
        return "superheros";
        
    }
    

    @PostMapping("/filterSightings")
    public String filterSightings(Model model, HttpServletRequest request, @ModelAttribute SightingDTO sightingBO,
            RedirectAttributes redirectAttrs) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        List<SuperHeroSighting> superHeroSights = null;
        
        int count = 0;
        try {
            List<Location> locations = locationRepo.findAll();        
            List<SuperHero> superHeros = superHeroRepo.findAll();
            
            model.addAttribute("locations", locations);
            model.addAttribute("superHero", superHeros);
            model.addAttribute("sightingObj",new SightingDTO());

            //To ensure only one Search Parameter is selected
            if(null != sightingBO.getDate()){
                count++;
            }
            if (null != request.getParameter("superHeroSelected")
                    && !StringUtils.isEmpty(request.getParameter("superHeroSelected"))) {
                count++;
            }
            if (null != request.getParameter("locationSelected")
                    && !StringUtils.isEmpty(request.getParameter("locationSelected"))) {
                count++;
            }
            
            if(count > 1) {
                model.addAttribute("searchmessage", "Select Only One Search Parameter");
            } else if(count ==0) {
                superHeroSights = superHeroSightingRepository.findAll();
                model.addAttribute("searchmessage", "ALL Sightings");
            } else {
                if(null != sightingBO.getDate()){
                    LocalDate localDate = LocalDate.parse(simpleDateFormat.format(sightingBO.getDate()));
                    superHeroSights = superHeroSightingRepository.findByDate(localDate);
                    model.addAttribute("searchmessage", "Sightings for date "+localDate);
                }

                if (null != request.getParameter("superHeroSelected")
                        && !StringUtils.isEmpty(request.getParameter("superHeroSelected"))) {
                    SuperHero superHero = superHeroRepo
                            .findById(Integer.valueOf(request.getParameter("superHeroSelected"))).orElse(null);
                    superHeroSights = superHeroSightingRepository.findBySuperHero(superHero);
                    model.addAttribute("searchmessage", "Sightings for SuperHero "+superHero.getName());
                }

                if (null != request.getParameter("locationSelected")
                        && !StringUtils.isEmpty(request.getParameter("locationSelected"))) {
                    Location location = locationRepo.findById(Integer.valueOf(request.getParameter("locationSelected")))
                            .orElse(null);
                    superHeroSights = superHeroSightingRepository.findByLocation(location);
                    model.addAttribute("searchmessage", "Sightings for Location "+location.getName());
                }
            }
            model.addAttribute("superHeroSighting", superHeroSights);

        }catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("searchmessage", "Error: Try Again!");

        }
        
        return "sightings";

    }
  
}
