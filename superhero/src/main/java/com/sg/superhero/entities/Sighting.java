/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.entities;

import java.sql.Date;
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

/**
 *
 * @author zeenatbaig
 */
@Entity
public class Sighting {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    
    @Column(name = "date", columnDefinition = "DATE")
    private  java.sql.Date date; 
    
    @ManyToOne
    @JoinColumn(name = "superheroorsupervillainid", nullable = false )
    private Superheroorsupervillain superhero;
    
     @ManyToOne
    @JoinColumn(name = "locationid", nullable = false )
    private Location location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Superheroorsupervillain getSuperhero() {
        return superhero;
    }

    public void setSuperhero(Superheroorsupervillain superhero) {
        this.superhero = superhero;
    }

   

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

   

    
    
}
