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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author zeenatbaig
 */
@Entity
public class Organization {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column
    @NotBlank(message = "Name must not be blank")
    @Size(max = 45, message="Name must be fewer than 45 characters")
    private String name;
    
    @Column
    private String description;
    
    @OneToOne
    @JoinColumn(name = "locationid", nullable = false)
    private Location location;
    
    @ManyToMany
    @JoinTable(name = "organization_superheroorsupervillain",
            joinColumns = {@JoinColumn(name = "organizationid")},
            inverseJoinColumns = {@JoinColumn(name = "superheroorsupervillainid")})
    private List<Superheroorsupervillain> superheros;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Superheroorsupervillain> getSuperheros() {
        return superheros;
    }

    public void setSuperheros(List<Superheroorsupervillain> superheros) {
        this.superheros = superheros;
    }

       
    
}
