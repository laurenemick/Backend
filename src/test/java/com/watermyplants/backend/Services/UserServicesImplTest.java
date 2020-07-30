package com.watermyplants.backend.Services;

import com.watermyplants.backend.BackendApplication;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserMinimum;
import com.watermyplants.backend.Repositories.PlantRepository;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.Repositories.UserRepository;
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
public class UserServicesImplTest {
    @Autowired
    private UserServices userServices;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private RoleRepository roleRepository;

    List<User> myList;

    @Before
    public void setUp() throws Exception {
        myList = userServices.listAll();

        for(User u: myList)
        {
            System.out.println(u.getUsername() + " " + u.getId());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void alistAll() {
        myList.clear();
        myList = userServices.listAll();
        assertEquals(4, myList.size());
    }

    @Test
    public void bsave() {
        User newUser = new User("testDude", "test@email.com", "123456789", "password");
        userServices.save(newUser);
        assertEquals(5, userServices.listAll().size());
    }

    @Test
    public void cfindByUsername() {
        assertEquals("testdude", userServices.findById(15).getUsername());
    }

    @Test
    public void dfindById() {

        assertEquals("testdude", userServices.findById(15).getUsername());
    }

    @Test
    public void eupdate() {
        UserMinimum original = new UserMinimum();
        original.setPhone("updatedPhoneNumber");
        original.setPassword("");
        original.setEmail("updated@updatedEmail.com");
        original.setUsername("");

        userServices.update(original, 15);

        assertEquals("updated@updatedEmail.com",userServices.findById(15).getEmail());
        assertEquals("updatedPhoneNumber", userServices.findById(15).getPhone());
    }

    @Test
    public void fdelete() {
        userServices.delete((long)15);
        myList = userServices.listAll();
        assertEquals(4, myList.size());
    }
}