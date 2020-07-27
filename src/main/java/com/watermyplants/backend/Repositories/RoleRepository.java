package com.watermyplants.backend.Repositories;

import com.watermyplants.backend.Models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>
{
    Role findByName(String name);
}
