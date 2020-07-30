package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Role;
import com.watermyplants.backend.Repositories.RoleRepository;
import com.watermyplants.backend.exceptions.ResourceFoundException;
import com.watermyplants.backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserAuditing userAuditing;

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

    @Override
    public List<Role> findAll() {
        List<Role> myList = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(myList::add);
        return  myList;
    }

    @Transactional
    @Override
    public Role save(Role role)
    {
        if (role.getUsers()
                .size() > 0)
        {
            throw new ResourceFoundException("User Roles are not updated through Role.");
        }

        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public Role update(long id,
                       Role role)
    {
        if (role.getName() == null)
        {
            throw new ResourceNotFoundException("No role name found to update!");
        }

        Role newRole = findRoleById(id); // see if id exists

        roleRepository.updateRoleName(userAuditing.getCurrentAuditor()
                        .get(),
                id,
                role.getName());
        return findRoleById(id);
    }
}
