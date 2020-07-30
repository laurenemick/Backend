package com.watermyplants.backend.Controllers;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Services.PlantService;
import com.watermyplants.backend.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController
{
    @Autowired
    private PlantService plantService;
    @Autowired
    private UserServices userServices;

    @GetMapping(value = "/plants")
    public ResponseEntity<?> listPlants(Authentication authentication)
    {
        List<Plant> myList = new ArrayList<>();
           myList = plantService.listPlants(authentication.getName());
           return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PostMapping(value = "/plant", consumes = "application/json")
    public ResponseEntity<?> addPlant(@RequestBody Plant newPlant, Authentication authentication)
    {
        User user = userServices.findByUsername(authentication.getName());
        plantService.addPlant(newPlant, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/plant/{id}", consumes = "application/json")
    public ResponseEntity<?> updatePlant(@PathVariable long id, @RequestBody Plant updatedPlant)
    {
        Plant updatedDone = plantService.update(id, updatedPlant);
        return new ResponseEntity<>(updatedDone, HttpStatus.OK);
    }

    @DeleteMapping(value = "/plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable long id)
    {
        plantService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
