package com.sg.superhero.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.superhero.entities.SuperPower;

@Repository
public interface SuperPowerRepository extends JpaRepository<SuperPower, Integer>{

}

