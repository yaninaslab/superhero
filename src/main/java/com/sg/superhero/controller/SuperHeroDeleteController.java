
package com.sg.superhero.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.SuperHero;
import com.sg.superhero.entities.SuperHeroSighting;
import com.sg.superhero.repositories.LocationRepository;
import com.sg.superhero.repositories.OrganizationRepository;
import com.sg.superhero.repositories.SuperHeroRepository;
import com.sg.superhero.repositories.SuperHeroSightingRepository;
import com.sg.superhero.repositories.SuperPowerRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SuperHeroDeleteController {
    
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
    
    
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationRepo.deleteById(id);
        
        redirectAttrs.addFlashAttribute("message", "Location Has Been Succesfully Deleted");
        return "redirect:/locations";
    }
    
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        organizationRepo.deleteById(id);
        
        redirectAttrs.addFlashAttribute("message", "Organization Has Been Succesfully Deleted");
        return "redirect:/organizations";
    }
    
    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        superHeroRepo.deleteById(id);
        
        redirectAttrs.addFlashAttribute("message", "SuperHero Has Been Succesfully Deleted");
        return "redirect:/superheros";
    }
    
    @GetMapping("deleteSightings")
    public String deleteSightings(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        LocalDate selectedDate = LocalDate.parse(request.getParameter("date"));
        
        SuperHero superHeroSelected = superHeroRepo.findById(Integer.valueOf(request.getParameter("superheroid"))).orElse(null);
        Location locationSelected = locationRepo.findById(Integer.valueOf(request.getParameter("locationid"))).orElse(null);
        
        List<SuperHeroSighting> sightings = superHeroSightingRepository.findByDateHeroAndLocation(selectedDate, superHeroSelected, locationSelected);
        
        superHeroSightingRepository.delete(sightings.get(0));
        
        redirectAttrs.addFlashAttribute("message", "SuperHero Sighting Has Been Succesfully Deleted");
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(HttpServletRequest request, RedirectAttributes redirectAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        superPowerRepository.deleteById(id);
        
        redirectAttrs.addFlashAttribute("message", "Super Power Has Been Succesfully Deleted");
        return "redirect:/superpowers";
    }
}
