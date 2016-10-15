package com.appdirect.quotes.db.dao.impl;

import com.appdirect.quotes.db.model.entities.Session;
import com.appdirect.quotes.db.model.entities.User;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
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

    public User findUserBySession(String sessionId) {
        Query query = currentSession().createQuery("FROM "+Session.class.getName() + " q WHERE q.sessionId=:sessionId");
        query.setParameter("sessionId", sessionId);
        if(query.list() == null || query.list().isEmpty())
            return null;
        return ((Session)query.list().get(0)).getUser();
    }

    public String getUserSessionIfExist(User user){
        Query query = currentSession().createQuery("FROM "+Session.class.getName() + " q WHERE q.user=:user");
        query.setParameter("user", user);
        if(query.list() == null || query.list().isEmpty())
            return null;
        return ((Session)query.list().get(0)).getSessionId();
    }

    public void delete(Session session){
        currentSession().delete(session);
    }

    public Session findSessionById(String sessionId){
        return get(sessionId);
    }
}
