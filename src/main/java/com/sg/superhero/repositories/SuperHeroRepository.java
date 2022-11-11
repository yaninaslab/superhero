package com.sg.superhero.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.superhero.entities.SuperHero;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, Integer>{

}

