package com.appdirect.quotes.db.dao;

import com.appdirect.quotes.db.model.entities.Quote;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.exception.GenericQuotesException;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class QuoteDao extends AbstractDAO<Quote> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public QuoteDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Quote findById(Long id) {
        return get(id);
    }

    public long create(Quote quote) {
        return persist(quote).getId();
    }

    public List<Quote> findAll() {
        return list(namedQuery("Quote.findAll"));
    }

    public List<Quote> findByUser(User user) {
        Query query = currentSession().createQuery("FROM "+Quote.class.getName() + " q WHERE q.user=:user");
        query.setParameter("user", user);
        return query.list();
    }

    public void deleteById(Long id){
        Quote quote = new Quote();
        quote.setId(id);
        currentSession().delete(quote);
    }

    public void updateQuote(Long id, String newQuote){
        Quote quote = findById(id);
        if(quote == null){
            String errorMessage = "Invalid Quote Id";
            logger.error(errorMessage);
            throw new GenericQuotesException(errorMessage);
        }

        quote.setQuote(newQuote);
        currentSession().update(quote);
    }
}
