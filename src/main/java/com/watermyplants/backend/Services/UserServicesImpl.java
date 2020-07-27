package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Repositories.UserRepository;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServicesImpl implements UserServices
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Override
    public List<User> listAll() {
        List<User> myList= new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(myList::add);
        return myList;
    }

    @Override
    public User save(User user)
    {
        User newUser = new User();

        if (user.getId() != 0)
        {
            userRepository.findById(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getId() + " not found!"));
            newUser.setId(user.getId());
        }

        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail()
                .toLowerCase());

        newUser.getRoles()
                .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleService.findRoleById(3);
            newUser.getRoles()
                    .add(new UserRoles(newUser, addRole));
        }

        return userRepository.save(newUser);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User " + id + " not found"));
    }
}
