package com.sg.superhero.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.superhero.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{

}

