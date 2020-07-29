package com.watermyplants.backend.Controllers;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Services.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/plants")
public class PlantController
{
    @Autowired
    private PlantService plantService;

    @GetMapping(value = "/plants")
    public ResponseEntity<?> listPlants(Principal principal)
    {
        List<Plant> myList = new ArrayList<>();
           myList = plantService.listPlants(principal.getName());
           return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PostMapping("/plant")
    public ResponseEntity<?> addPlant(@RequestBody Plant newPlant, Principal principal)
    {
        plantService.addPlant(newPlant, principal.getName());
        List<Plant> myList = plantService.listPlants(principal.getName());
        return new ResponseEntity<>(myList, HttpStatus.CREATED);
    }

    @PutMapping("/plant/{id}")
    public ResponseEntity<?> updatePlant(@PathVariable long id, @RequestBody Plant updatedPlant, Principal principal)
    {
        Plant updatedDone = plantService.update(id, updatedPlant, principal.getName());
        return new ResponseEntity<>(updatedDone, HttpStatus.OK);
    }

    @DeleteMapping("/plant/{id}")
    public ResponseEntity<?> deletePlant(@PathVariable long id, Principal principal)
    {
        plantService.delete(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
