package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.QuoteDaoImpl;
import com.appdirect.quotes.db.dao.impl.SessionDaoImpl;
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

    private final QuoteDaoImpl quoteDaoImpl;
    private final SessionDaoImpl sessionDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public QuoteServiceImpl(QuoteDaoImpl quoteDaoImpl, SessionDaoImpl sessionDao){
        this.quoteDaoImpl = quoteDaoImpl;
        this.sessionDao = sessionDao;
    }

    private User getUserFromSessionId(String sessionId){
        User user = sessionDao.findUserBySession(sessionId);
        if(user == null) {
            String errorMessage = "Invalid session";
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        return user;
    }

    private void validateObjects(User user, Quote quote){
        if(quote == null){
            String errorMessage = "Invalid Quote Id";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }

        if(!user.equals(quote.getUser())){
            String errorMessage = "User "+user.getUserName()+" doesnot own this quote";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }
    }

    @Override
    public void createQuote(String sessionId, QuoteCreateRequest quoteCreateRequest) {

        try {
            User user = getUserFromSessionId(sessionId);
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
    public void updateQuote(String sessionId, QuoteUpdateRequest quoteUpdateRequest) {
        try {
            User user = getUserFromSessionId(sessionId);
            Quote quote = quoteDaoImpl.findById(quoteUpdateRequest.getQuoteId());

            validateObjects(user, quote);
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
    public QuoteResponse getQuote(Long id, String sessionId) {
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
    public QuoteResponse getQuoteForUser(String sessionId) {
        User user = getUserFromSessionId(sessionId);
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
    public void deleteQuote(Long quoteId, String sessionId) {
        User user = getUserFromSessionId(sessionId);
        Quote quote = quoteDaoImpl.findById(quoteId);

        validateObjects(user, quote);

        quoteDaoImpl.delete(quote);
    }
}
