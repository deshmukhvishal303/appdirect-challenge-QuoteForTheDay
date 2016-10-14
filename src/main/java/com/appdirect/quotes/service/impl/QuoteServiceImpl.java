package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.QuoteDao;
import com.appdirect.quotes.db.dao.UserDao;
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

    UserDao userDao;
    QuoteDao quoteDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public QuoteServiceImpl(UserDao userDao, QuoteDao quoteDao){
        this.userDao = userDao;
        this.quoteDao = quoteDao;
    }

    @Override
    public void createQuote(QuoteCreateRequest quoteCreateRequest) {

        try {
            User user = userDao.findByUserName(quoteCreateRequest.getUserName());
            Quote quote = new Quote();
            quote.setQuote(quoteCreateRequest.getQuote());
            quote.setUser(user);

            quoteDao.create(quote);
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
            quoteDao.updateQuote(quoteId, newQuote);
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
        Quote quote = quoteDao.findById(id);

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
        User user = userDao.findByUserName(userName);
        if(user == null){
            String errorMessage = "User Not Found";
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        List<Quote> quoteList = quoteDao.findByUser(user);

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
        Quote quote = quoteDao.findById(quoteId);

        if(quote == null){
            String errorMessage = "Invalid Quote Id";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }

        quoteDao.deleteById(quoteId);
    }
}
