package com.sg.superhero.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.superhero.entities.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer>{
    

}

