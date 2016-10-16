package com.appdirect.quotes.controller;

import com.appdirect.quotes.dto.auth.OAuthSignature;
import com.appdirect.quotes.dto.event.EventResponse;
import com.appdirect.quotes.exception.FieldNotFoundException;
import com.appdirect.quotes.exception.SubscriptionConflictException;
import com.appdirect.quotes.service.api.SubscriptionService;
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
 * Created by Vishal Deshmukh on 16/10/16.
 */
@Path("/subscription")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/subscription", description = "Subscription API's")
public class SubscriptionController {

    private SubscriptionService subscriptionService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @POST
    @Path("/create?event_url={event_url}")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "Create Subscription", response = Response.class)
    public Response createSubscription(@QueryParam(value = "event_url") String eventUrl, OAuthSignature oAuthSignature){
        Response response;
        EventResponse eventResponse = new EventResponse();
        try {
            eventResponse = subscriptionService.createSubscription(eventUrl, oAuthSignature);
            response = Response.status(Response.Status.CREATED).entity(eventResponse).build();
        } catch(BadRequestException bre) {
            eventResponse.setErrorCode(400);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Bad Request");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        } catch (FieldNotFoundException fnfe){
            eventResponse.setErrorCode(404);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Company or User not found");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        } catch (SubscriptionConflictException sce){
            eventResponse.setErrorCode(409);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Subscription already exists");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        } catch (Exception e){
            eventResponse.setErrorCode(500);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Internal Server Error Occurred");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        }

        return response;
    }

    @DELETE
    @Path("/cancel?event_url={event_url}")
    @Timed
    @UnitOfWork
    @ApiOperation(value = "Cancel Subscription", response = Response.class)
    public Response cancelSubscription(@QueryParam(value = "event_url") String eventUrl, OAuthSignature oAuthSignature){
        Response response;
        EventResponse eventResponse = new EventResponse();
        try {
            subscriptionService.cancelSubscription(eventUrl, oAuthSignature);
            eventResponse.setMessage("Subscription Cancelled successfully");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        } catch(BadRequestException bre) {
            eventResponse.setErrorCode(400);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Bad Request");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        } catch (Exception e){
            eventResponse.setErrorCode(500);
            eventResponse.setSuccess(false);
            eventResponse.setMessage("Internal Server Error Occurred");
            response = Response.status(Response.Status.OK).entity(eventResponse).build();
        }

        return response;
    }
}
