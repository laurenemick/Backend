package com.watermyplants.backend.Services;

import com.watermyplants.backend.BackendApplication;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Repositories.PlantRepository;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.Repositories.UserRepository;
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
public class UserServicesImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private RoleRepository roleRepository;

    List<User> myList;

    @Before
    public void setUp() throws Exception {
        myList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(myList::add);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void alistAll() {
        assertEquals(4, myList.size());
    }

    @Test
    public void bsave() {
        User newUser = new User("testDude", "test@email.com", "123456789", "password");
        userRepository.save(newUser);
        assertEquals("testDude", userRepository.findByUsername("testDude").getUsername());
    }

    @Test
    public void cfindByUsername() {
        assertEquals("testDude", userRepository.findByUsername("testDude").getUsername());
    }

    @Test
    public void dfindById() {

    }

    @Test
    public void eupdate() {
    }

    @Test
    public void fdelete() {
    }
}