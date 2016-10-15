package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.QuoteDaoImpl;
import com.appdirect.quotes.db.dao.impl.UserDaoImpl;
import com.appdirect.quotes.db.model.entities.Quote;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.dto.quote.QuoteCreateRequest;
import com.appdirect.quotes.dto.quote.QuoteResponse;
import com.appdirect.quotes.dto.quote.QuoteUpdateRequest;
import com.appdirect.quotes.exception.GenericQuotesException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.QuoteService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishal Deshmukh on 14/10/16.
 */
public class QuoteServiceImpl implements QuoteService{

    UserDaoImpl userDaoImpl;
    QuoteDaoImpl quoteDaoImpl;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public QuoteServiceImpl(UserDaoImpl userDaoImpl, QuoteDaoImpl quoteDaoImpl){
        this.userDaoImpl = userDaoImpl;
        this.quoteDaoImpl = quoteDaoImpl;
    }

    @Override
    public void createQuote(QuoteCreateRequest quoteCreateRequest) {

        try {
            User user = userDaoImpl.findByUserName(quoteCreateRequest.getUserName());
            Quote quote = new Quote();
            quote.setQuote(quoteCreateRequest.getQuote());
            quote.setUser(user);

            quoteDaoImpl.create(quote);
        } catch(UserNotFoundException unfe){
            String errorMessage = unfe.getErrorMessage();
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        } catch (Exception e){
            String errorMessage = "Error occurred while creating quote";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }
    }

    @Override
    public void updateQuote(QuoteUpdateRequest quoteUpdateRequest) {
        try {
            Long quoteId = quoteUpdateRequest.getQuoteId();
            String newQuote = quoteUpdateRequest.getQuote();
            quoteDaoImpl.updateQuote(quoteId, newQuote);
        } catch (GenericQuotesException gqe){
            throw gqe;
        } catch (Exception e){
            String errorMessage = "Error occurred while updating quote";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }
    }

    @Override
    public QuoteResponse getQuote(Long id) {
        Quote quote = quoteDaoImpl.findById(id);

        if(quote == null){
            String errorMessage = "Invalid Quote Id";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }

        List<String> quoteList = new ArrayList<>();
        quoteList.add(quote.getQuote());
        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setQuote(quoteList);
        return quoteResponse;
    }

    @Override
    public QuoteResponse getQuoteForUser(String userName) {
        User user = userDaoImpl.findByUserName(userName);
        if(user == null){
            String errorMessage = "User Not Found";
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        List<Quote> quoteList = quoteDaoImpl.findByUser(user);

        if(quoteList == null || quoteList.isEmpty())
            return new QuoteResponse();

        List<String> quoteResponseList = new ArrayList<>();
        quoteList.forEach( q -> quoteResponseList.add(q.getQuote()));
        QuoteResponse quoteResponse = new QuoteResponse();
        quoteResponse.setQuote(quoteResponseList);
        return quoteResponse;
    }

    @Override
    public void deleteQuote(Long quoteId) {
        Quote quote = quoteDaoImpl.findById(quoteId);

        if(quote == null){
            String errorMessage = "Invalid Quote Id";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }

        quoteDaoImpl.deleteById(quoteId);
    }
}
