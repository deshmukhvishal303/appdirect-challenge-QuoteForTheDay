package com.appdirect.quotes.controller;

import com.appdirect.quotes.dto.generic.FailureResponse;
import com.appdirect.quotes.dto.generic.SuccessResponse;
import com.appdirect.quotes.dto.quote.QuoteCreateRequest;
import com.appdirect.quotes.dto.quote.QuoteResponse;
import com.appdirect.quotes.dto.quote.QuoteUpdateRequest;
import com.appdirect.quotes.exception.GenericQuotesException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.QuoteService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Path("/quote")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteController {
    private QuoteService quoteService;

    @Inject
    public QuoteController(QuoteService quoteService){
        this.quoteService = quoteService;
    }

    @POST
    @Path("/create")
    @Timed
    @UnitOfWork
    public Response createQuote(QuoteCreateRequest quoteCreateRequest){
        Response response;
        try{
            quoteService.createQuote(quoteCreateRequest);
            String message = "Quote Created Successfully!!!";
            SuccessResponse successResponse = new SuccessResponse(message);

            response = Response.status(Response.Status.OK).entity(successResponse).build();
        } catch (UserNotFoundException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (GenericQuotesException gqe){
            FailureResponse failureResponse = new FailureResponse(gqe.getErrorMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @PUT
    @Path("/update")
    @Timed
    @UnitOfWork
    public Response updateQuote(QuoteUpdateRequest quoteUpdateRequest){
        Response response;
        try{
            quoteService.updateQuote(quoteUpdateRequest);
            String message = "Quote Updated Successfully!!!";
            SuccessResponse successResponse = new SuccessResponse(message);

            response = Response.status(Response.Status.OK).entity(successResponse).build();
        } catch (UserNotFoundException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (GenericQuotesException gqe){
            FailureResponse failureResponse = new FailureResponse(gqe.getErrorMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @DELETE
    @Path("/delete/{quote_id}")
    @Timed
    @UnitOfWork
    public Response deleteQuote(@PathParam("quote_id") Long id){
        Response response;
        try {
            quoteService.deleteQuote(id);
            response = Response.status(Response.Status.OK).build();
        } catch (GenericQuotesException gqe){
            FailureResponse failureResponse = new FailureResponse(gqe.getErrorMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @GET
    @Path("/retrieve/{quote_id}")
    @Timed
    @UnitOfWork
    public Response retrieveQuote(@PathParam("quote_id") Long id){
        Response response;
        try {
            QuoteResponse quoteResponse = quoteService.getQuote(id);

            response = Response.status(Response.Status.OK).entity(quoteResponse).build();
        } catch (GenericQuotesException gqe){
            FailureResponse failureResponse = new FailureResponse(gqe.getErrorMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }

    @GET
    @Path("/retrieve/quote/{user}")
    @Timed
    @UnitOfWork
    public Response retrieveQuoteForUser(@PathParam("user") String user){
        Response response;
        try {
            QuoteResponse quoteResponse = quoteService.getQuoteForUser(user);

            if(quoteResponse.getQuote() == null || quoteResponse.getQuote().isEmpty())
                response = Response.status(Response.Status.NO_CONTENT).build();
            else
                response = Response.status(Response.Status.OK).entity(quoteResponse).build();
        } catch (UserNotFoundException unfe){
            FailureResponse failureResponse = new FailureResponse(unfe.getErrorMessage());
            response = Response.status(Response.Status.BAD_REQUEST).entity(failureResponse).build();
        } catch (GenericQuotesException gqe){
            FailureResponse failureResponse = new FailureResponse(gqe.getErrorMessage());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        } catch (Exception e){
            FailureResponse failureResponse = new FailureResponse();
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(failureResponse).build();
        }

        return response;
    }
}
