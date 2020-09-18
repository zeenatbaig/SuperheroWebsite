/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.repositories;

import com.sg.superhero.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author zeenatbaig
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer>{
    
}
