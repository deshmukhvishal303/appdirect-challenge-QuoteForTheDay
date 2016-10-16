package com.appdirect.quotes.db.dao.impl;

import com.appdirect.quotes.db.model.entities.Profile;
import com.appdirect.quotes.db.model.entities.Quote;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class ProfileDaoImpl extends AbstractDAO<Profile> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public ProfileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Profile findById(Long id) {
        return get(id);
    }

    public long create(Profile profile) {
        return persist(profile).getId();
    }

    public List<Profile> findAll() {
        return list(namedQuery("Profile.findAll"));
    }

    public List<Profile> findByOpenId(String openId) {
        Query query = currentSession().createQuery("FROM "+Quote.class.getName() + " q WHERE q.open_id=:open_id");
        query.setParameter("open_id", openId);
        return query.list();
    }

    public void deleteById(Long id){
        Quote quote = new Quote();
        quote.setId(id);
        currentSession().delete(quote);
    }

    public void delete(Profile profile){
        currentSession().delete(profile);
    }
}
