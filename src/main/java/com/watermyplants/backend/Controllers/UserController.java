package com.watermyplants.backend.Controllers;

import com.watermyplants.backend.Models.User;
import com.watermyplants.backend.Services.UserServices;
import io.swagger.annotations.Authorization;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController
{
    @Autowired
    UserServices userServices;

    @GetMapping(value = "/users")
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myList = userServices.listAll();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    @PostMapping(value = "/user")
    public ResponseEntity<?> newUser(@Validated @RequestBody User newUser) throws URISyntaxException
    {
        newUser.setId(0);
        newUser = userServices.save(newUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/myinfo")
    @ResponseBody
    public ResponseEntity<?> getUserInfo(Principal principal)
    {
        User u = userServices.findByUsername(principal.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}
