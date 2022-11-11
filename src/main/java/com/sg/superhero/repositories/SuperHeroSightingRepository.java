package com.sg.superhero.repositories;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.SuperHero;
import com.sg.superhero.entities.SuperHeroSighting;

@Repository
public interface SuperHeroSightingRepository extends JpaRepository<SuperHeroSighting, Integer>{
    List<SuperHeroSighting> findByLocation(Location location);
    List<SuperHeroSighting> findBySuperHero(SuperHero superHero);
    
    @Query("SELECT s FROM SuperHeroSighting s WHERE s.sightingdate = ?1")
    List<SuperHeroSighting> findByDate(LocalDate date);
    
    @Query("SELECT s FROM SuperHeroSighting s WHERE s.sightingdate = ?1 and s.superHero = ?2 and s.location = ?3")
    List<SuperHeroSighting> findByDateHeroAndLocation(LocalDate date, SuperHero superHero, Location location);
}

