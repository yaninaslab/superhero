package com.sg.superhero.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


@Controller
public class SuperHeroCreateController {
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
    
    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();
    Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();
    Set<ConstraintViolation<SuperPower>> superPowerViolations = new HashSet<>();
    Set<ConstraintViolation<SuperHero>> superHeroViolations = new HashSet<>();
    Set<ConstraintViolation<SuperHeroSighting>> superHeroSightingsViolations = new HashSet<>();
    

    @PostMapping("/addLocation")
    public String addLocation(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        Location location = new Location();
        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        locationViolations = validate.validate(location);
        
        if(locationViolations.isEmpty()) {        
        try {
            location = locationRepo.save(location);
            redirectAttrs.addFlashAttribute("message", "Location Has Been Succesfully Added");
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Adding Location");
        }
        }else {
            redirectAttrs.addFlashAttribute("errors", locationViolations);
        }
        return "redirect:/locations";
    }
    

    @PostMapping("/addOrganization")
    public String addOrganization(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        Organization org = new Organization();
        org.setName(request.getParameter("name"));
        org.setDescription(request.getParameter("description"));
        org.setAddress(request.getParameter("address"));
        org.setContact(request.getParameter("contact"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        organizationViolations = validate.validate(org);
        
        if (organizationViolations.isEmpty()) {        
        try {
            org = organizationRepo.save(org);
            redirectAttrs.addFlashAttribute("message", "Organization Has Been Succesfully Added");
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Adding Organization");
        }
        }else {
            redirectAttrs.addFlashAttribute("errors", organizationViolations);
        }
        
        return "redirect:/organizations";
    }
    
    @PostMapping("/addSuperPower")
    public String addSuperPower(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        SuperPower power = new SuperPower();
        power.setName(request.getParameter("name"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        superPowerViolations = validate.validate(power);
        
        if (superPowerViolations.isEmpty()) {        
        try {
            power = superPowerRepository.save(power);
            redirectAttrs.addFlashAttribute("message", "Super Power Has Been Succesfully Added");
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Adding Super Power");
        }
         }else {
            redirectAttrs.addFlashAttribute("errors", superPowerViolations);
        }
        
        return "redirect:/superpowers";
    }
    

    @PostMapping("/addSuperHero")
    public String addSuperHero(HttpServletRequest request, @ModelAttribute SuperHeroDTO superHeroDto,
            RedirectAttributes redirectAttrs) {
        SuperHero superHero = new SuperHero();

        superHero.setName(request.getParameter("name"));
        superHero.setDescription(request.getParameter("description"));
        superHero.setIsvillan(superHeroDto.isVillan());
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        superHeroViolations = validate.validate(superHero);
        
        if (superHeroViolations.isEmpty()) {

        try {

            if (null != request.getParameter("superPowerSelected")
                    && !StringUtils.isEmpty(request.getParameter("superPowerSelected"))) {
                SuperPower superPower = superPowerRepository
                        .findById(Integer.valueOf(request.getParameter("superPowerSelected"))).orElse(null);
                superHero.setSuperpower(superPower);
            }

            if (null != superHeroDto.getOrglist() && superHeroDto.getOrglist().size() > 0) {
                List<Organization> organizations;
                organizations = new ArrayList<Organization>();
                superHeroDto.getOrglist().forEach(orgId -> {
                    organizations.add(organizationRepo.findById(orgId).orElse(null));
                });
                superHero.setOrganizations(organizations);
            }

            superHero = superHeroRepo.save(superHero);
            redirectAttrs.addFlashAttribute("message", "SuperHero Has Been Succesfully Added");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Adding SuperHero");
        }
                 }else {
            redirectAttrs.addFlashAttribute("errors", superHeroViolations);
        }

        return "redirect:/superheros";
    }
    

    @PostMapping("/addSightings")
    public String addSightings(HttpServletRequest request, @ModelAttribute SightingDTO sightingDto,
            RedirectAttributes redirectAttrs) {
        SuperHeroSighting superHeroSight = new SuperHeroSighting();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        System.out.println(sightingBO.getDate());

//         Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//         superHeroSightingsViolations = validate.validate(superHeroSight);
        
//        if (superHeroSightingsViolations.isEmpty()) {
        try {
            LocalDate localDate = LocalDate.parse(simpleDateFormat.format(sightingDto.getDate()));
            
            superHeroSight.setSightingdate(localDate);
            
            if (null != request.getParameter("superHeroSelected")
                    && !StringUtils.isEmpty(request.getParameter("superHeroSelected"))) {
                SuperHero superHero = superHeroRepo
                        .findById(Integer.valueOf(request.getParameter("superHeroSelected"))).orElse(null);
                superHeroSight.setSuperHero(superHero);
            }
            
            if (null != request.getParameter("locationSelected")
                    && !StringUtils.isEmpty(request.getParameter("locationSelected"))) {
                Location location = locationRepo
                        .findById(Integer.valueOf(request.getParameter("locationSelected"))).orElse(null);
                superHeroSight.setLocation(location);
            }
            
            superHeroSight = superHeroSightingRepository.save(superHeroSight);
            redirectAttrs.addFlashAttribute("message", "Sighting Has Been Succesfully Added");
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Adding Sighting");
        }
//        }else {
//            redirectAttrs.addFlashAttribute("errors", superHeroSightingsViolations);
//        }
        

        return "redirect:/sightings";
    }
  
}
