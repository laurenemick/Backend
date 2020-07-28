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
        User myUser = userRepository.findByUsername(name);
        for(Plant p: myUser.getPlants())
        {
            myList.add(p);
        }
        return myList;
    }

    @Override
    public void addPlant(Plant newPlant, String name) {
        newPlant.setUser(userRepository.findByUsername(name));
        userRepository.findByUsername(name).getPlants().add(newPlant);
    }

    @Override
    public void update(long id, String name) {
        User u = userRepository.findByUsername(name);
        Plant p = plantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Plant " + id + " not found"));
        Boolean found = false;

        for(Plant pu: u.getPlants())
        {
            if(pu.getUser().getUsername() == u.getUsername())
            {
                found = true;
            }
        }

        if(found == false)
        {
            plantRepository.findById((long) -1).orElseThrow(()->new ResourceNotFoundException("Plant " + id + " does not belong to user " + u.getUsername()));
        }else
        {
            plantRepository.deleteById(id);
        }
    }
}
