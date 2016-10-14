package com.appdirect.quotes.controller;

import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.api.UserService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    private UserService userService;

    @Inject
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public User getUserById(@PathParam("id")LongParam id){
        return userService.getUser(id.get());
    }
}
