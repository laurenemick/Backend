package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.User;

import java.util.List;

public interface PlantService
{
    List<Plant> listPlants(String name);
    void addPlant(Plant newPlant, String name);
    Plant update(long id, Plant updatedPlant, String name);
    Plant findById(long id);
    void delete(long id, String Name);
}
