package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findRoleById(long roleid) {
        Role foundRole = roleRepository.findById(roleid)
                .orElseThrow(()->new ResourceNotFoundException("Role " + roleid + " not found"));
        return foundRole;
    }

    @Override
    public Role findByName(String name)
    {
        Role foundRole = roleRepository.findByName(name);
        return foundRole;
    }
}
