package com.watermyplants.backend.Services;

import com.watermyplants.backend.BackendApplication;
import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.exceptions.ResourceFoundException;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantServiceImplTest {
    @Autowired
    private PlantService plantService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RoleService roleService;

    List<Plant> myList;


    @Before
    public void setUp() throws Exception {
        myList = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void alistPlants() {
        assertEquals(3, plantService.listPlants("nick").size());
    }

    @Test
    public void baddPlant() {
        User user = userServices.findByUsername("nick");
        Plant newPlant = new Plant("addplant", "addplant", "addplant", "addplant", user);
        plantService.addPlant(newPlant, user);
        assertEquals(4, plantService.listPlants("nick").size());
    }

    @Test
    public void cupdate() {
        Plant updatePlant = plantService.findById(15);
//        System.out.println(updatePlant.getNickname() + " " + updatePlant.getPlantid());
        updatePlant.setNickname("updatedname");
        updatePlant = plantService.update(15,updatePlant);
        assertEquals("updatedname", plantService.findById(15).getNickname());
    }

    @Test
    public void dfindById() {
        assertEquals("Daphne", plantService.findById(12).getNickname());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void edelete() {
        plantService.delete(15);
        assertEquals("updatedname", plantService.findById(15));
    }
}