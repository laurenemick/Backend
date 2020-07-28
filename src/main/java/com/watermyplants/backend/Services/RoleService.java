package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {
    Role findRoleById(long id);
    Role findByName(String name);
    List<Role> findAll();
    Role save(Role newRole);
    Role update(long id, Role newRole);
}

