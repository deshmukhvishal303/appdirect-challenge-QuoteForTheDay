package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.SessionDaoImpl;
import com.appdirect.quotes.db.model.entities.Session;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.api.SessionService;
import com.google.inject.Inject;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by Vishal Deshmukh on 15/10/16.
 */
public class SessionServiceImpl implements SessionService {

    private final SessionDaoImpl sessionDao;

    @Inject
    public SessionServiceImpl(SessionDaoImpl sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public String createSession(User user) {
        String sessionId = sessionDao.getUserSessionIfExist(user);

        if(sessionId == null || sessionId.isEmpty())
            sessionId = UUID.randomUUID().toString();

        Session session = new Session();
        session.setUser(user);
        session.setSessionId(sessionId);
        session.setExpirationTime(DateTime.now().plusMonths(6));
        sessionDao.saveSession(session);
        return sessionId;
    }

    @Override
    public void deleteSession(Session session) {
        sessionDao.delete(session);
    }

    @Override
    public Session getSessionFromId(String sessionId) {
        return sessionDao.findSessionById(sessionId);
    }
}
