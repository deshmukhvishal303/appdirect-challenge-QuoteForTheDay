package com.appdirect.quotes.service.api;

import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.impl.SessionServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 15/10/16.
 */
@ImplementedBy(SessionServiceImpl.class)
public interface SessionService {
    String createSession(User user);
}
