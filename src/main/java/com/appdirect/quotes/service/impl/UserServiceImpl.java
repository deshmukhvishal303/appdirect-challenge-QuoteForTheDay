package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.UserDao;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.service.api.UserService;
import com.google.inject.Inject;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class UserServiceImpl implements UserService{

    UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id);
    }
}
