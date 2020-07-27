package com.watermyplants.backend.Services;

import com.watermyplants.backend.Models.User;

import java.util.List;

public interface UserServices
{
    List<User> listAll();

    User save(User user);

    User findByUsername(String name);

    User findById(long id);
}
