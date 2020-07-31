package com.watermyplants.backend.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Services.PlantService;
import com.watermyplants.backend.Services.UserServices;
import org.h2.table.Plan;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PlantControllerTest.class)
@RunWith(SpringRunner.class)
@WithMockUser(username = "admin", roles = "ADMIN")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = PlantController.class)
public class PlantControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;
    @MockBean
    private UserServices userServices;

    private List<User> myUsers;

    @Before
    public void setUp() throws Exception {
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

        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "", u2));
        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "", u2));
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
    public void listPlants() throws Exception
    {
        String apiUrl = "/plants/plants";
        List<Plant>myList = new ArrayList<>();
        for(Plant p: myUsers.get(1).getPlants())
        {
            myList.add(p);
        }
        Mockito.when(plantService.listPlants(any(String.class))).thenReturn(myList);
        Mockito.when(userServices.findByUsername(any(String.class))).thenReturn(myUsers.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
//                        .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(myUsers.get(1).getPlants());

        assertEquals(er, tr);
    }

    @Test
    public void addPlant() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String apiUrl  = "/plants/plant";
        Plant newPlant = new Plant("plantName", "plantspecies", "plantwatering", "plantimageurl", myUsers.get(2));
        newPlant.setPlantid(100);

        String plantString = mapper.writeValueAsString(newPlant);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(plantString);

        mockMvc.perform(rb).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updatePlant() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        String apiUrl  = "/plants/plant/100";
        Plant newPlant = new Plant("plantName", "plantspecies", "plantwatering", "plantimageurl", myUsers.get(2));

        String plantString = mapper.writeValueAsString(newPlant);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(plantString);

        mockMvc.perform(rb).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deletePlant() throws Exception
    {
        String apiUrl = "/plants/plant/1";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(rb).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
    }
}