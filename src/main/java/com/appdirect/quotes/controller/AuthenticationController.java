package com.appdirect.quotes.controller;

import com.appdirect.quotes.dto.generic.FailureResponse;
import com.appdirect.quotes.dto.generic.SuccessResponse;
import com.appdirect.quotes.dto.user.UserSignInRequest;
import com.appdirect.quotes.dto.user.UserSignInResponse;
import com.appdirect.quotes.dto.user.UserSignUpRequest;
import com.appdirect.quotes.exception.DuplicateUserException;
import com.appdirect.quotes.exception.InvalidCredentialsException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.AuthenticationService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/authentication", description = "Authentication API's")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("/user/signup")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "User SignUp", response = Response.class)
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
    @ApiOperation(value = "User SignIn", response = Response.class)
    public Response signIn(UserSignInRequest userSignInRequest){
        Response response;
        try{
            UserSignInResponse userSignInResponse = authenticationService.signInUser(userSignInRequest);

            logger.info("User "+userSignInRequest.getUserName()+" SignedIn Successfully!!!");

            response = Response.status(Response.Status.OK).entity(userSignInResponse).build();
        } catch (UserNotFoundException | InvalidCredentialsException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @DELETE
    @Path("/user/signout")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "User SignOut", response = Response.class)
    public Response signOut(@HeaderParam("X_SESSION_ID") String sessionId){
        Response response;
        try{
            authenticationService.signOutUser(sessionId);

            logger.info("User SignedOut Successfully!!!");

            response = Response.status(Response.Status.NO_CONTENT).build();
        } catch (UserNotFoundException | InvalidCredentialsException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }
}
