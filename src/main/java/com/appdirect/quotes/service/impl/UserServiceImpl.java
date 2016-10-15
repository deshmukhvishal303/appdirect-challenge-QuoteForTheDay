package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.UserDaoImpl;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.api.UserService;
import com.google.inject.Inject;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class UserServiceImpl implements UserService{

    UserDaoImpl userDaoImpl;

    @Inject
    public UserServiceImpl(UserDaoImpl userDaoImpl){
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public User getUser(Long id) {
        return userDaoImpl.findById(id);
    }
}
