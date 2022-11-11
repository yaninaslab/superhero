
package com.sg.superhero.controller;

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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SuperHeroEditController {
    
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
    
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationRepo.findById(id).orElse(null);
        
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String updateLocation(@Valid Location location, BindingResult result, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "editLocation";
        }
        Location existingLocation = locationRepo.findById(location.getId()).orElse(null);
        
        existingLocation.setName(location.getName());
        existingLocation.setDescription(location.getDescription());
        existingLocation.setAddress(location.getAddress());
        existingLocation.setLatitude(location.getLatitude());
        existingLocation.setLongitude(location.getLongitude());

        locationRepo.save(existingLocation);
        redirectAttrs.addFlashAttribute("message", "Location Has Been Succesfully Updated");
        return "redirect:/locations";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = organizationRepo.findById(id).orElse(null);
        
        model.addAttribute("organization", org);
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String updateOrganization(@Valid Organization org, BindingResult result, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "editOrganization";
        }
        Organization existingOrg = organizationRepo.findById(org.getId()).orElse(null);
        
        existingOrg.setName(existingOrg.getName());
        existingOrg.setDescription(existingOrg.getDescription());
        existingOrg.setAddress(existingOrg.getAddress());
        existingOrg.setContact(existingOrg.getContact());

        organizationRepo.save(existingOrg);
        redirectAttrs.addFlashAttribute("message", "Organization Has Been Succesfully Updated");
        return "redirect:/organizations";
    }
    
    @GetMapping("editSuperHero")
    public String editSuperHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperHero superhero = superHeroRepo.findById(id).orElse(null);
        
        List<Organization> orgs = organizationRepo.findAll();
        List<SuperPower> superPowers = superPowerRepository.findAll();
        List<SuperHero> superHero = superHeroRepo.findAll();
        model.addAttribute("organizations", orgs);
        model.addAttribute("superpowers", superPowers);
        model.addAttribute("superHeroObj",new SuperHeroDTO());
        model.addAttribute("superhero", superhero);
        return "editSuperHero";
    }
    
    @PostMapping("editSuperHero")
    public String updateSuperHero(@Valid SuperHero superhero, BindingResult result, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "editSuperHero";
        }
        SuperHero currenthero = superHeroRepo.findById(superhero.getId()).orElse(null);
        
        currenthero.setName(superhero.getName());
        currenthero.setDescription(superhero.getDescription());
        currenthero.setIsvillan(superhero.isIsvillan());
        currenthero.setSuperpower(superhero.getSuperpower());
        currenthero.setOrganizations(superhero.getOrganizations());

        superHeroRepo.save(currenthero);
        redirectAttrs.addFlashAttribute("message", "SuperHero Has Been Succesfully Updated");
        return "redirect:/superheros";
    }
    
    @GetMapping("editSightings")
    public String editSightings(HttpServletRequest request, Model model) {
        LocalDate selectedDate = LocalDate.parse(request.getParameter("date"));
        ZoneId defaultZoneId = ZoneId.systemDefault();

        SuperHero superHeroSelected = superHeroRepo.findById(Integer.valueOf(request.getParameter("superheroid"))).orElse(null);
        Location locationSelected = locationRepo.findById(Integer.valueOf(request.getParameter("locationid"))).orElse(null);
        
        System.out.println(request.getParameter("date"));
        System.out.println(request.getParameter("superheroid"));

        System.out.println(request.getParameter("locationid"));
        System.out.println(selectedDate);
        
        List<SuperHeroSighting> sightings = superHeroSightingRepository.findByDateHeroAndLocation(selectedDate, superHeroSelected, locationSelected);
        
        List<Location> locations = locationRepo.findAll();        
        List<SuperHero> superHero = superHeroRepo.findAll();
        
        SightingDTO sightingDTO = new SightingDTO();
        sightingDTO.setDate(Date.from(selectedDate.atStartOfDay(defaultZoneId).toInstant()));
        
        model.addAttribute("superHeroSighting", sightings.get(0));
        model.addAttribute("locations", locations);
        model.addAttribute("superHero", superHero);
        model.addAttribute("sightingObj",sightingDTO);
        
        superHeroSightingRepository.delete(sightings.get(0));
        
        return "editSightings";
    }
    
    @PostMapping("updateSightings")
    public String updateSightings(SuperHeroSighting superHeroSighting,
            HttpServletRequest request, RedirectAttributes redirectAttrs, @ModelAttribute SightingDTO sightingBO) {
        SuperHeroSighting superHeroSight = new SuperHeroSighting();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            LocalDate localDate = LocalDate.parse(simpleDateFormat.format(sightingBO.getDate()));
            
            superHeroSight.setSightingdate(localDate);
            superHeroSight.setSuperHero(superHeroSighting.getSuperHero());
            superHeroSight.setLocation(superHeroSighting.getLocation());
            
            superHeroSight = superHeroSightingRepository.save(superHeroSight);
            redirectAttrs.addFlashAttribute("message", "Sighting Has Been Succesfully Updated");
        }catch(Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("message", "Error Updating Sighting");
        }
        return "redirect:/sightings";
    }
    
    @GetMapping("editSuperPower")
    public String editSuperPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        SuperPower superpower = superPowerRepository.findById(id).orElse(null);
        
        model.addAttribute("superpower", superpower);
        return "editSuperPower";
    }
    
    @PostMapping("editSuperPower")
    public String editSuperPower(@Valid SuperPower power, BindingResult result, RedirectAttributes redirectAttrs) {
        if(result.hasErrors()) {
            return "editSuperPower";
        }
        SuperPower currentPower = superPowerRepository.findById(power.getId()).orElse(null);
        
        currentPower.setName(power.getName());

        superPowerRepository.save(currentPower);
        redirectAttrs.addFlashAttribute("message", "Super Power Has Been Succesfully Updated");
        return "redirect:/superpowers";
    }
}
