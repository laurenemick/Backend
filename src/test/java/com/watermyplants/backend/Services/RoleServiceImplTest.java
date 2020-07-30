package com.watermyplants.backend.Services;

import com.watermyplants.backend.BackendApplication;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Repositories.RoleRepository;
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
public class RoleServiceImplTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserServices userServices;

    List<Role> myList;

    @Before
    public void setUp() throws Exception {
        myList= roleService.findAll();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findRoleById() {
        assertEquals("ADMIN", roleService.findRoleById(1).getName());
    }

    @Test
    public void findByName() {
        assertEquals(1, roleService.findByName("ADMIN").getRoleid());
    }

    @Test
    public void findAll() {
        myList = roleService.findAll();
        assertEquals(3, myList.size());
    }

    @Test
    public void save() {
        Role newRole = new Role("testRole");
        newRole = roleService.save(newRole);
        assertEquals("TESTROLE",roleService.findRoleById(15).getName());
    }

    @Test
    public void update() {
        Role updateRole = roleService.findRoleById(15);
        User newUser = new User("testuser", "email@fortest.com", "1234567890", "password");
        newUser.getRoles().add(new UserRoles(newUser,updateRole));
        userServices.save(newUser);
        updateRole.setName("updatedName");
        updateRole = roleService.update(15, updateRole);
        assertEquals("UPDATEDNAME", roleService.findRoleById(15).getName());
    }
}