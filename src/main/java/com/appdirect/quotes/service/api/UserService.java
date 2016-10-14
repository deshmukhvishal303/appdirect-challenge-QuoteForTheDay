package com.appdirect.quotes.service.api;

import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.impl.UserServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@ImplementedBy(UserServiceImpl.class)
public interface UserService {
    User getUser(Long id);
}
