package com.watermyplants.backend.Controllers;
import com.watermyplants.backend.Models.User;

import com.watermyplants.backend.Models.UserMinimum;
import com.watermyplants.backend.Models.UserRoles;
import com.watermyplants.backend.Services.RoleService;
import com.watermyplants.backend.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class allows access to endpoints that are open to all users regardless of authentication status.
 * Its most important function is to allow a person to create their own username
 */
@RestController
public class OpenController
{
    /**
     * A method in this controller adds a new user to the application so needs access to User Services to do this.
     */
    @Autowired
    private UserServices userService;

    /**
     * A method in this controller adds a new user to the application with the role User so needs access to Role Services to do this.
     */
    @Autowired
    private RoleService roleService;

    /**
     * This endpoint always anyone to create an account with the default role of USER. That role is hardcoded in this method.
     *
     * @param httpServletRequest the request that comes in for creating the new user
     * @param newminuser         A special minimum set of data that is needed to create a new user
     * @return The token access and other relevent data to token access. Status of CREATED. The location header to look up the new user.
     * @throws URISyntaxException we create some URIs during this method. If anything goes wrong with that creation, an exception is thrown.
     */
    @PostMapping(value = "/registeruser",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addSelf(
            HttpServletRequest httpServletRequest,
            @Valid
            @RequestBody
                    UserMinimum newminuser)
            throws
            URISyntaxException
    {
        // Create the user
        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setEmail(newminuser.getEmail());

        // add the default role of user
        Set<UserRoles> newRoles = new HashSet<>();
        newRoles.add(new UserRoles(newuser,
                roleService.findByName("USER")));
        newuser.setRoles(newRoles);

        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        // The location comes from a different controller!
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newuser.getId())
                .toUri();
        responseHeaders.setLocation(newUserURI);


        return new ResponseEntity<>(responseHeaders,
                HttpStatus.CREATED);
    }

    /**
     * Prevents no favicon.ico warning from appearing in the logs. @ApiIgnore tells Swagger to ignore documenting this as an endpoint.
     */
    @ApiIgnore
    @GetMapping("favicon.ico")
    public void returnNoFavicon()
    {

    }

}
