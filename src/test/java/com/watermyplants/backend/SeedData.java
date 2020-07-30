package com.watermyplants.backend;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Repositories.PlantRepository;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        //reset data
        userRepository.deleteAll();
        roleRepository.deleteAll();
        plantRepository.deleteAll();

        //create 3 main roles, admin user and data
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        Role role3 = new Role("DATA");

        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);
        role3 = roleRepository.save(role3);

        List<Role> myList = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(myList::add);
        for(Role r: myList)
        {
            System.out.println("Role:" + r.getName() + " " + r.getRoleid());
        }
        //make sample users starting with the admin acc
        User u1 = new User("admin", "admin@admin.com", "12345678", "password");
        u1.getRoles().add(new UserRoles(u1, role1));
        u1.setPassword("password");
        User u2 = new User("nick", "test1@admin.com", "12345678", "password");
        u2.getRoles().add(new UserRoles(u2, role2));
        User u3 = new User("lucy", "test2@admin.com", "12345678", "password");
        u3.getRoles().add(new UserRoles(u3, role2));
        User u4 = new User("fred", "test3@admin.com", "12345678", "password");
        u4.getRoles().add(new UserRoles(u4, role2));

        u1 = userRepository.save(u1);
        u2 = userRepository.save(u2);
        u3 = userRepository.save(u3);
        u4 = userRepository.save(u4);

        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "",u2));
        u2.getPlants().add(new Plant("Bob", "fancyGreen", "once a day", "",u2));
        u2.getPlants().add(new Plant("Jeff", "ugleGreen", "once a day", "", u2));
        u3.getPlants().add(new Plant("Diane", "notGreen", "once a day", "", u3));
        u3.getPlants().add(new Plant("Daphne", "sortaYellow", "once a day", "", u3));
        u4.getPlants().add(new Plant("Spiduh", "Orange", "once a day", "", u4));
        u4.getPlants().add(new Plant("Greeny", "cactus", "once a day", "", u4));

    }
}
