package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Repositories.PlantRepository;
import com.watermyplants.backend.Repositories.UserRepository;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "plantService")
public class PlantServiceImpl implements PlantService
{
    @Autowired
    PlantRepository plantRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Plant> listPlants(String name) {
        List<Plant> myList = new ArrayList<>();
        User user = userRepository.findByUsername(name);
        for(Plant p:user.getPlants())
        {
            myList.add(p);
        }
        return myList;
    }

    @Override
    public void addPlant(Plant newPlant, User user) {
        long userId = newPlant.getPlantid();
        Plant addedPlant = new Plant();
        addedPlant.setImageurl(newPlant.getImageurl());
        addedPlant.setSpecies(newPlant.getSpecies());
        addedPlant.setNickname(newPlant.getNickname());
        addedPlant.setH2ofrequency(newPlant.getH2ofrequency());
        addedPlant.setPlantid(0);
        addedPlant.setUser(user);
        newPlant = plantRepository.save(addedPlant);

    }

    @Override
    public Plant update(long id, Plant updatedPlant) {
        Plant p = plantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Plant " + id + " not found"));

        if(updatedPlant.getH2ofrequency() != null)
        {
            p.setH2ofrequency(updatedPlant.getH2ofrequency());
        }
        if(updatedPlant.getImageurl() != null)
        {
            p.setImageurl(updatedPlant.getImageurl());
        }
        if(updatedPlant.getSpecies() != null)
        {
            p.setSpecies(updatedPlant.getSpecies());
        }
        if(updatedPlant.getNickname() != null)
        {
            p.setNickname(updatedPlant.getNickname());
        }

        return plantRepository.save(p);
    }

    @Override
    public Plant findById(long id) {
        return plantRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Plant " + id + " not found"));
    }

    @Override
    public void delete(long id) {
        plantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Plant " + id + " not found"));
        plantRepository.deleteById(id);
    }
}