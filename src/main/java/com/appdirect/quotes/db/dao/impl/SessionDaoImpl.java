package com.appdirect.quotes.db.dao.impl;

import com.appdirect.quotes.db.model.entities.Session;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

/**
 * Created by Vishal Deshmukh on 15/10/16.
 */
public class SessionDaoImpl extends AbstractDAO<Session> {
    @Inject
    public SessionDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void saveSession(Session session) {
        persist(session);
    }
}
