package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PlantServiceImplTest {
    @Autowired
    private PlantService plantService;

    @Autowired
    private UserServices userServices;

    List<Plant> myList;
    User testUser;

    @Before
    public void setUp() throws Exception {
        testUser= new User()
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listPlants() {
    }

    @Test
    public void addPlant() {
    }

    @Test
    public void update() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void delete() {
    }
}