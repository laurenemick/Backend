package com.watermyplants.backend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermyplants.backend.Models.*;
import com.watermyplants.backend.Services.SecurityUserServiceImpl;
import com.watermyplants.backend.Services.UserServices;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.ProfileValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserControllerTest.class)
@RunWith(SpringRunner.class)
@WithMockUser( roles = "ADMIN")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = UserController.class)
public class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userServices;

    private List<User> myUsers;

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        myUsers = new ArrayList<>();
        //create 3 main roles, admin user and data
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        Role role3 = new Role("DATA");

        //make sample users starting with the admin acc
        User u1 = new User("admin", "admin@admin.com", "12345678", "password");
        u1.getRoles().add(new UserRoles(u1, role1));
        u1.setPassword("password");
        u1.setId(1);
        User u2 = new User("nick", "test1@admin.com", "12345678", "password");
        u2.getRoles().add(new UserRoles(u2, role2));
        u2.setId(2);
        User u3 = new User("lucy", "test2@admin.com", "12345678", "password");
        u3.getRoles().add(new UserRoles(u3, role2));
        u3.setId(3);
        User u4 = new User("fred", "test3@admin.com", "12345678", "password");
        u4.getRoles().add(new UserRoles(u4, role2));
        u4.setId(4);

        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "",u2));
        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "",u2));
        u2.getPlants().add(new Plant("Jeff", "ugleGreen", "once a day", "", u2));
        u3.getPlants().add(new Plant("Diane", "notGreen", "once a day", "", u3));
        u3.getPlants().add(new Plant("Daphne", "sortaYellow", "once a day", "", u3));
        u4.getPlants().add(new Plant("Spiduh", "Orange", "once a day", "", u4));
        u4.getPlants().add(new Plant("Greeny", "cactus", "once a day", "", u4));

        myUsers.add(u1);
        myUsers.add(u2);
        myUsers.add(u3);
        myUsers.add(u4);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception
    {
        String apiUrl = "/users/users";
        Mockito.when(userServices.listAll()).thenReturn(myUsers);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUsers);

        assertEquals(er, tr);
    }

    @Test
    public void getUserInfo() throws Exception
    {
        String apiUrl = "/users/myinfo";
        Mockito.when(userServices.findByUsername(any(String.class))).thenReturn(myUsers.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUsers.get(1));

        assertEquals(er, tr);;
    }

    @Test
    public void updateUser() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String apiUrl = "/users/user/2";

        User updatedUser = myUsers.get(2);
        updatedUser.setPhone("updatedphone");

        UserMinimum newUser = new UserMinimum();
        newUser.setEmail(updatedUser.getEmail());
        newUser.setPassword(updatedUser.getPassword());
        newUser.setPhone(updatedUser.getPhone());
        newUser.setUsername(updatedUser.getUsername());

        Mockito.when(userServices.update(newUser, 2)).thenReturn(updatedUser);

        String userString = mapper.writeValueAsString(newUser);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUser() throws Exception
    {
        String apiUrl = "/users/user/2";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(rb).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());

    }
}