/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author zeenatbaig
 */
@Entity 
public class Superheroorsupervillain {
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message="Name must be fewer than 45 characters")
    private String name;
    
    @Column
    @Size(max = 255, message = "Description must be fewer than 300 characters")
    private String description;
   
    @ManyToMany(mappedBy = "superheros")
    private List< Organization > organizations;
    
     @OneToOne
    @JoinColumn(name = "superpowerid")
    
    private Superpower superpower;


    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }
    
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
    
   

   

   

   
}
