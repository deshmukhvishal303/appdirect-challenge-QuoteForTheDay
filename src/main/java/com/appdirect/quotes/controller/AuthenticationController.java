package com.appdirect.quotes.controller;

import com.appdirect.quotes.dto.generic.FailureResponse;
import com.appdirect.quotes.dto.generic.SuccessResponse;
import com.appdirect.quotes.dto.user.UserSignInRequest;
import com.appdirect.quotes.dto.user.UserSignUpRequest;
import com.appdirect.quotes.exception.DuplicateUserException;
import com.appdirect.quotes.exception.InvalidCredentialsException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.AuthenticationService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Inject
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("/user/signup")
    @Timed
    @UnitOfWork
    public Response signUp(UserSignUpRequest userSignUpRequest){
        Response response;
        try{
            authenticationService.signUpUser(userSignUpRequest);

            String message = "User SignedUp Successfully!!!";
            SuccessResponse successResponse = new SuccessResponse(message);

            response = Response.status(Response.Status.OK).entity(successResponse).build();
        } catch (DuplicateUserException due){
            FailureResponse failureResponse = new FailureResponse(due.getErrorMessage());
            response = Response.status(Response.Status.CONFLICT).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @POST
    @Path("/user/signin")
    @Timed
    @UnitOfWork
    public Response signIn(UserSignInRequest userSignInRequest){
        Response response;
        try{
            authenticationService.signInUser(userSignInRequest);

            String message = "User SignedIn Successfully!!!";
            SuccessResponse successResponse = new SuccessResponse(message);

            response = Response.status(Response.Status.OK).entity(successResponse).build();
        } catch (UserNotFoundException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (InvalidCredentialsException ice){
            FailureResponse failureResponse = new FailureResponse(ice.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }
}
