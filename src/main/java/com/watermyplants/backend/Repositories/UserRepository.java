package com.watermyplants.backend.Repositories;

import com.watermyplants.backend.Models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String name);
}
