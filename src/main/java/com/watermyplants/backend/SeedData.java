package com.watermyplants.backend;

import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Repositories.PlantRepository;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
        Role role1 = new Role("ADMIN");
        role1.setRoleid(1);
        Role role2 = new Role("USER");
        Role role3 = new Role("DATA");
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        User a1 = new User("admin", "admin@admin.com", "12345678", "password");
        a1.setPasswordNoEncrypt("password");
        a1.setId(1);
        a1.getRoles().add(new UserRoles(a1,role1));

        userRepository.save(a1);
    }
}
