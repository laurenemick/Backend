package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.Role;
import org.springframework.stereotype.Service;

public interface RoleService {
    Role findRoleById(long id);
    Role findByName(String name);
}
