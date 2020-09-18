/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author zeenatbaig
 */
@Entity
public class Location {
    
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
    
    @Column
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 100, message = "Address must be fewer than 100 characters")
    private String address;
    
    @Column
    @NotNull(message = "Longitude name must not be empty.")
    @DecimalMin(value = "-180.1", inclusive = false)
    @DecimalMax(value = "180.1", inclusive = false)
    @Digits(integer=9, fraction=1, message = "Longitude must be 9 digits with 1 decimal place.")
    private BigDecimal longitude;
   
    
     @Column
    @NotNull(message = "Latitude name must not be empty.")
    @DecimalMin(value = "-180.1", inclusive = false)
    @DecimalMax(value = "180.1", inclusive = false)
    @Digits(integer=9, fraction=1, message = "Latitude must be 9 digits with 1 decimal place.")
    private BigDecimal latitude;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

 
    
    
}
