package com.portfolio.exerciseapp.controller;

import com.portfolio.exerciseapp.dao.UserDao;
import com.portfolio.exerciseapp.model.LoginDto;
import com.portfolio.exerciseapp.model.LoginResponseDto;
import com.portfolio.exerciseapp.model.RegisterUserDto;
import com.portfolio.exerciseapp.model.User;
import com.portfolio.exerciseapp.security.jwt.JWTFilter;
import com.portfolio.exerciseapp.security.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * AuthenticationController is a class used for handling requests to authenticate Users.
 *
 * It depends on an instance of a UserDao for retrieving and storing user data. This is provided
 * through dependency injection.
 */
@RestController
@CrossOrigin
public class AuthenticationController {

    // JWT Token provider
    private final TokenProvider tokenProvider;
    // Spring Framework class for handling authentication
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    // DAO for accessing user information from the database
    private UserDao userDao;

    /*
     * Constructor uses Spring dependency injection to get instances of dependent classes at runtime.
     */
    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user = userDao.getByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        if ( userDao.userExists(newUser.getUsername()) ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exists.");
        } else {
            userDao.create(newUser.getUsername(),newUser.getPassword(), newUser.getRole());
        }
    }
}


