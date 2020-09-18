/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author zeenatbaig
 */

@Entity
public class Superpower {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
  
    @Column
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message="Power must be fewer than 45 characters")
    private String power;

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    } 
    
    
}
