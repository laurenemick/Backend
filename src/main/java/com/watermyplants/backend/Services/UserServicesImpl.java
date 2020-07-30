package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Plant;
import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Repositories.UserRepository;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServicesImpl implements UserServices
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    PlantService plantService;

    @Override
    public List<User> listAll() {
        List<User> myList= new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(myList::add);
        return myList;
    }

    @Transactional
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
        newUser.setPhone(user.getPhone());

        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail()
                .toLowerCase());

        newUser.getRoles()
                .clear();
        for (UserRoles ur : user.getRoles())
        {
            Role addRole = roleService.findByName("USER");
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

    @Transactional
    @Override
    public User update(User user, long id) {
            User updateUser = userRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("User " + id + " not found"));
            if(user.getUsername() != null)
            {
                updateUser.setUsername(user.getUsername());
            }
            if(user.getPassword() != null && user.getPassword() != "")
            {
                updateUser.setPasswordNoEncrypt(user.getPassword());
            }
            if(user.getEmail() != null)
            {
                updateUser.setEmail(user.getEmail());
            }

            if(user.getPhone() != null)
            {
                updateUser.setPhone(user.getPhone());
            }

            if(user.getPlants().size() > 0)
            {
                for(Plant p: user.getPlants())
                {
                    Plant currentPlant = plantService.findById(p.getPlantid());
                    currentPlant.setUser(updateUser);
                }
            }
            return userRepository.save(updateUser);

    }

    @Transactional
    @Override
    public void delete(long id, User user) {
        if(user.getId() != id)
        {
            throw new ResourceNotFoundException("You may only delete you own account!");
        }else
        {
            userRepository.deleteById(id);
        }
    }
}
