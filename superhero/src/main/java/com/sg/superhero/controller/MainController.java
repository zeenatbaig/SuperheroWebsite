/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superhero.controller;

import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.Sighting;
import com.sg.superhero.entities.Superheroorsupervillain;
import com.sg.superhero.entities.Superpower;
import com.sg.superhero.repositories.LocationRepository;
import com.sg.superhero.repositories.OrganizationRepository;
import com.sg.superhero.repositories.SightingRepository;
import com.sg.superhero.repositories.SuperheroOrSupervillainRepository;
import com.sg.superhero.repositories.SuperpowerRepository;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import static net.bytebuddy.implementation.bytecode.member.MethodVariableAccess.store;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author zeenatbaig
 */
@Controller
public class MainController {
       @Autowired
       LocationRepository locations;
       
       @Autowired
       OrganizationRepository organizations;
       
       @Autowired
       SightingRepository sightings;
       
       @Autowired 
       SuperheroOrSupervillainRepository superherosOrSupervillains;
       
       @Autowired
       SuperpowerRepository superpowers;
       
       
       Set<ConstraintViolation<Superheroorsupervillain>> superViolations = new HashSet<>();
       
       Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();
       
       Set<ConstraintViolation<Superpower>> superpowerViolations = new HashSet<>();
       
        Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();
       
       
       //----------------------------------------------------------------------
       //-------------Locations-----------------------------------------------
       //----------------------------------------------------------------------
       
       @GetMapping("locations")
       public String displayLocations(Model model){
           model.addAttribute("locations", locations.findAll());
           model.addAttribute("errors", locationViolations);
           return "locations";
       }
       
       @PostMapping("addLocation")
       public String addLocation(Location location){
           Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           locationViolations = validate.validate(location);

            if(locationViolations.isEmpty()) {
                locations.save(location);
            }
           
           return "redirect:/locations";
       }
       
         @GetMapping("editLocation")
        public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
           Optional<Location> dbLocation = locations.findById(id);
            if(dbLocation.isPresent()) {
            Location location = dbLocation.get();
           model.addAttribute("location",location);
            }else{
                
            }
        return "editLocation";
    }
        
        
          @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Location> dbLocation = locations.findById(id);
         if(dbLocation.isPresent()) {
            Location location = dbLocation.get();
            location.setName(request.getParameter("name"));
            location.setDescription(request.getParameter("description"));
            location.setAddress(request.getParameter("address"));
            BigDecimal longitude =  new BigDecimal(request.getParameter("longitude"));
            BigDecimal latitude =  new BigDecimal(request.getParameter("latitude"));
            location.setLongitude(longitude);
            location.setLatitude(latitude);
            
            
             Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           locationViolations = validate.validate(location);

            if(locationViolations.isEmpty()) {
                locations.save(location);
            }
             
            
            //operate on existingCustomer
        } else {
    
        }
     
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
           Location location = locations.findById(id).orElse(null);
           
           List<Sighting > findByLocationSighting = sightings.findByLocation(location);
           for(Sighting deleteSighting : findByLocationSighting){
               int sightingId = deleteSighting.getId();
               sightings.deleteById(sightingId);
           }
           
           List<Organization> findByLocationOrgan = organizations.findByLocation(location);
           for(Organization deleteOrganization : findByLocationOrgan){
               int organizationId = deleteOrganization.getId();
               organizations.deleteById(organizationId);
           }
        locations.deleteById(id);
        return "redirect:/locations";
    }
    
     @GetMapping("deleteLocationPage")
        public String deleteLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
           Optional<Location> dbLocation = locations.findById(id);
            if(dbLocation.isPresent()) {
            Location location = dbLocation.get();
           model.addAttribute("location",location);
            }else{
                
            }
        return "deleteLocation";
    }
    
     //----------------------------------------------------------------------
    //-------------Locations-----------------------------------------------
   //----------------------------------------------------------------------
    
     //----------------------------------------------------------------------
    //-------------Superheros-----------------------------------------------
   //----------------------------------------------------------------------
    
    @GetMapping("superherosOrSupervillains")
           public String displaySuperheros(Model model){
           model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
           model.addAttribute("superpowers", superpowers.findAll());
           model.addAttribute("organizations", organizations.findAll());
           model.addAttribute("errors", superViolations);
           return "superherosOrSupervillains";
       }
           
     @PostMapping("addSuperheroOrSupervillain")
       public String addSuperheroOrSupervillain(Superheroorsupervillain superheroorsupervillain, HttpServletRequest request){
           int superpowerId = Integer.parseInt(request.getParameter("superpowerId"));
           
           if(request.getParameterValues("organizationId") != null){
               
                String[] organizationIds = request.getParameterValues("organizationId");
                List<Organization> dBorganzations = new ArrayList<>();
                for(String organizationId : organizationIds){
                    dBorganzations.add(organizations.findById(Integer.parseInt(organizationId)).orElse(null));
                }
                
                superheroorsupervillain.setOrganizations(dBorganzations);
           }
           
           Superpower superpower = superpowers.findById(superpowerId).orElse(null);
           superheroorsupervillain.setSuperpower(superpower);
           superheroorsupervillain.setName(request.getParameter("name"));
            superheroorsupervillain.setDescription(request.getParameter("description"));
          
             Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           superViolations = validate.validate(superheroorsupervillain);

            if(superViolations.isEmpty()) {
                superherosOrSupervillains.save(superheroorsupervillain);
            }
           
          
           return "redirect:/superherosOrSupervillains";
       }
       
        @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superheroorsupervillain superherosOrSupervillain = superherosOrSupervillains.findById(id).orElse(null);
        model.addAttribute("superherosOrSupervillain", superherosOrSupervillain);
        return "superheroDetail";
    }
        @GetMapping("editSuperheros")
    public String editSuperheros(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Superheroorsupervillain superheroorsupervillain = superherosOrSupervillains.findById(id).orElse(null);
      
        model.addAttribute("superherosOrSupervillain", superheroorsupervillain);
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("superpowers", superpowers.findAll());
        return "editSuperheros";
    }
    
       @PostMapping("editSuperheros")
        public String performEditSuperheros( Superheroorsupervillain superheroorsupervillain,HttpServletRequest request) {
         int locationId = Integer.parseInt(request.getParameter("superpowerId"));
           
           if(request.getParameterValues("organizationId") != null){
               
                String[] organizationIds = request.getParameterValues("organizationId");
                List<Organization> dbOrganizations = new ArrayList<>();
                for(String organizationId : organizationIds){
                    dbOrganizations.add(organizations.findById(Integer.parseInt(organizationId)).orElse(null));
                }
                
                superheroorsupervillain.setOrganizations(dbOrganizations);
           }
           
           Superpower superpower = superpowers.findById(locationId).orElse(null);
           superheroorsupervillain.setSuperpower(superpower);
           superheroorsupervillain.setName(request.getParameter("name"));
            superheroorsupervillain.setDescription(request.getParameter("description"));
          
            
              Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           superViolations = validate.validate(superheroorsupervillain);

            if(superViolations.isEmpty()) {
                superherosOrSupervillains.save(superheroorsupervillain);
            }
           
           
           return "redirect:/superherosOrSupervillains";
      
           
    }
        
         @GetMapping("deleteSuperherosPage")
        public String deleteSuperherosPage(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Superheroorsupervillain superheroorsupervillain = superherosOrSupervillains.findById(id).orElse(null);
      
        model.addAttribute("superherosOrSupervillain", superheroorsupervillain);
        model.addAttribute("organizations", organizations.findAll());
        model.addAttribute("superpowers", superpowers.findAll());
        return "deleteSuperheros";
    }
       
         @GetMapping("deleteSuperheros")
    public String deleteSuperheros(Integer id) {
        
           Superheroorsupervillain supers = superherosOrSupervillains.findById(id).orElse(null);
           
         List<Sighting > findBysuperSighting = sightings.findBySuperhero(supers);
           for(Sighting deleteSighting : findBysuperSighting){
               int sightingId = deleteSighting.getId();
               sightings.deleteById(sightingId);
           }
           
            List<Organization> findBySuper = organizations.findBySuperheros(supers);
           for(Organization deleteOrganization : findBySuper){
               int organizationId = deleteOrganization.getId();
               organizations.deleteById(organizationId);
           }
        superherosOrSupervillains.deleteById(id);
        return "redirect:/superherosOrSupervillains";
    }
           
    //----------------------------------------------------------------------
    //-------------Superheros-----------------------------------------------
   //----------------------------------------------------------------------
           
     //----------------------------------------------------------------------
    //-------------Organizations-----------------------------------------------
   //----------------------------------------------------------------------
        
        @GetMapping("organizations")
           public String displayOrganizations(Model model){
           model.addAttribute("organizations", organizations.findAll());
           model.addAttribute("locations", locations.findAll());
           model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
           model.addAttribute("errors", organizationViolations);
           return "organizations";
       }
           
        @PostMapping("addOrganization")
       public String addOrganization(Organization organization, HttpServletRequest request){
           int locationId = Integer.parseInt(request.getParameter("locationid"));
           
           if(request.getParameterValues("superherosOrSupervillainId") != null){
               
                String[] superheroIds = request.getParameterValues("superherosOrSupervillainId");
                List<Superheroorsupervillain> superheros = new ArrayList<>();
                for(String superheroId : superheroIds){
                    superheros.add(superherosOrSupervillains.findById(Integer.parseInt(superheroId)).orElse(null));
                }
                
                organization.setSuperheros(superheros);
           }
           
           Location location = locations.findById(locationId).orElse(null);
           organization.setLocation(location);
           organization.setName(request.getParameter("name"));
            organization.setDescription(request.getParameter("description"));
         
            
            
             Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           organizationViolations = validate.validate(organization);

            if(organizationViolations.isEmpty()) {
               organizations.save(organization);
            }
         
           return "redirect:/organizations";
       }
       
        @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizations.findById(id).orElse(null);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
    
      @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizations.deleteById(id);
        return "redirect:/organizations";
    }
    
      @GetMapping("deleteOrganizationPage")
    public String deleteOrganizationPage(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Organization organization = organizations.findById(id).orElse(null);
      
        model.addAttribute("organization", organization);
        model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
        model.addAttribute("locations", locations.findAll());
        return "deleteOrganizationPage";
    }
    
    
      @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Organization organization = organizations.findById(id).orElse(null);
      
        model.addAttribute("organization", organization);
        model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
        model.addAttribute("locations", locations.findAll());
        return "editOrganization";
    }
    
       @PostMapping("editOrganization")
        public String performEditOrganization( Organization organization,HttpServletRequest request) {
         int locationId = Integer.parseInt(request.getParameter("locationid"));
           
           if(request.getParameterValues("superherosOrSupervillainId") != null){
               
                String[] superheroIds = request.getParameterValues("superherosOrSupervillainId");
                List<Superheroorsupervillain> superheros = new ArrayList<>();
                for(String superheroId : superheroIds){
                    superheros.add(superherosOrSupervillains.findById(Integer.parseInt(superheroId)).orElse(null));
                }
                
                organization.setSuperheros(superheros);
           }
           
           Location location = locations.findById(locationId).orElse(null);
           organization.setLocation(location);
           organization.setName(request.getParameter("name"));
            organization.setDescription(request.getParameter("description"));
          
            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           organizationViolations = validate.validate(organization);

            if(organizationViolations.isEmpty()) {
               organizations.save(organization);
            }
           return "redirect:/organizations";
        
    }
    //----------------------------------------------------------------------
    //-------------Organizations-----------------------------------------------
   //----------------------------------------------------------------------     
        
     //----------------------------------------------------------------------
    //-------------Superpowers-----------------------------------------------
   //----------------------------------------------------------------------      
           
       @GetMapping("superpowers")
           public String displaySuperpowers(Model model){
           model.addAttribute("superpowers", superpowers.findAll());
           model.addAttribute("errors", superpowerViolations);

           return "superpowers";
       }
           
       @PostMapping("addSuperpower")
       public String addSuperpower(Superpower superpower){
            Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           superpowerViolations = validate.validate(superpower);

            if(superpowerViolations.isEmpty()) {
               superpowers.save(superpower);
            }
           return "redirect:/superpowers";
       }
       
        @GetMapping("editSuperpower")
        public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
           Optional<Superpower> dbSuperpower = superpowers.findById(id);
            if(dbSuperpower.isPresent()) {
            Superpower superpower = dbSuperpower.get();
           model.addAttribute("superpower",superpower);
            }else{
                
            }
        return "editSuperpower";
    }
        
        @PostMapping("editSuperpower")
    public String performEditSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Superpower> dbSuperpower = superpowers.findById(id);
         if(dbSuperpower.isPresent()) {
            Superpower superpower = dbSuperpower.get();
            superpower.setPower(request.getParameter("power"));
            
           Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
           superpowerViolations = validate.validate(superpower);

            if(superpowerViolations.isEmpty()) {
               superpowers.save(superpower);
            }
            
            //operate on existingCustomer
        } else {
    
        }
     
        return "redirect:/superpowers";
    }
    
      @GetMapping("deleteSuperpower")
    public String deleteSuperpower(Integer id) {
        Superpower superpower = superpowers.findById(id).orElse(null);
        
        List<Superheroorsupervillain> findBySuperpower = superherosOrSupervillains.findBySuperpower(superpower);
        for(Superheroorsupervillain superDelete: findBySuperpower){
            
             deleteSuperheros(superDelete.getId());
        }
       
        /*
         List<Sighting > findBysuperSighting = sightings.findBySuperhero(supers);
           for(Sighting deleteSighting : findBysuperSighting){
               int sightingId = deleteSighting.getId();
               sightings.deleteById(sightingId);
           }
        
           
           List<Superheroorsupervillain> findBySuperpower = superherosOrSupervillains.findBySuperpower(superpower);
           for(Superheroorsupervillain deleteSuper : findBySuperpower){
               int superId = deleteSuper.getId();
               superherosOrSupervillains.deleteById(superId);
           }*/
        superpowers.deleteById(id);
        return "redirect:/superpowers";
    }
    
     @GetMapping("deleteSuperpowerPage")
        public String deleteSuperpowerPage(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
           Optional<Superpower> dbSuperpower = superpowers.findById(id);
            if(dbSuperpower.isPresent()) {
            Superpower superpower = dbSuperpower.get();
           model.addAttribute("superpower",superpower);
            }else{
                
            }
        return "deleteSuperpowerPage";
    }
    
     //----------------------------------------------------------------------
    //-------------Superpowers-----------------------------------------------
   //----------------------------------------------------------------------  
    
     //----------------------------------------------------------------------
    //-------------Sightings-----------------------------------------------
   //----------------------------------------------------------------------  
    
    
        @GetMapping("sightings")
           public String displaySightings(Model model){
           model.addAttribute("sightings", sightings.findAll());
           model.addAttribute("locations", locations.findAll());
           model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
           return "sightings";
       } 
           
       @PostMapping("addSighting")
       public String addSighting(Sighting sighting, HttpServletRequest request) throws ParseException{
           int locationId = Integer.parseInt(request.getParameter("locationId"));
           int supeheroId = Integer.parseInt(request.getParameter("superheroOrSupervillainId"));
          
           
           Superheroorsupervillain superheroOrSupervillian = superherosOrSupervillains.findById(supeheroId).orElse(null);
           Location location = locations.findById(locationId).orElse(null);     
                
           
           sighting.setSuperhero(superheroOrSupervillian);
           sighting.setLocation(location);
          
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(request.getParameter("date"));
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
                      sighting.setDate(sqlStartDate);
                      
           sightings.save(sighting);
           
           return "redirect:/sightings";
       }
       
         @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightings.deleteById(id);
        return "redirect:/sightings";
    }
    
     @GetMapping("deleteSightingPage")
    public String deleteSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Sighting sighting = sightings.findById(id).orElse(null);
      
        model.addAttribute("sighting", sighting);
        model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
        model.addAttribute("locations", locations.findAll());
        return "deleteSightingPage";
    }
    
     @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
       
        Sighting sighting = sightings.findById(id).orElse(null);
      
        model.addAttribute("sighting", sighting);
        model.addAttribute("superherosOrSupervillains", superherosOrSupervillains.findAll());
        model.addAttribute("locations", locations.findAll());
        return "editSighting";
    }
    
    @PostMapping("editSighting")
       public String editSighting(Sighting sighting, HttpServletRequest request) throws ParseException{
           int locationId = Integer.parseInt(request.getParameter("locationId"));
           int supeheroId = Integer.parseInt(request.getParameter("superheroOrSupervillainId"));
          
           
           Superheroorsupervillain superheroOrSupervillian = superherosOrSupervillains.findById(supeheroId).orElse(null);
           Location location = locations.findById(locationId).orElse(null);     
                
           
           sighting.setSuperhero(superheroOrSupervillian);
           sighting.setLocation(location);
          
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(request.getParameter("date"));
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());  
                      sighting.setDate(sqlStartDate);
                      
           sightings.save(sighting);
           
           return "redirect:/sightings";
       }
           
   //----------------------------------------------------------------------
    //-------------Sightings-----------------------------------------------
   //----------------------------------------------------------------------          
           
        @GetMapping("/")
        public String index(Model model) {
        model.addAttribute("sightings", sightings.findAll());
        return "index";
        }
        
       
           
       
}
