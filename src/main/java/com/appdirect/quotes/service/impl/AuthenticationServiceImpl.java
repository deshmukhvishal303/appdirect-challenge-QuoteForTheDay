package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.UserDao;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.dto.user.UserSignInRequest;
import com.appdirect.quotes.dto.user.UserSignUpRequest;
import com.appdirect.quotes.exception.DuplicateUserException;
import com.appdirect.quotes.exception.InvalidCredentialsException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.AuthenticationService;
import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDao userDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public AuthenticationServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void signUpUser(UserSignUpRequest userSignUpRequest) {
        try {
            userDao.findByUserName(userSignUpRequest.getEmailId());

            String errorMessage = "User "+userSignUpRequest.getEmailId()+" already exists in system.";
            logger.error(errorMessage);

            throw new DuplicateUserException(errorMessage);
        } catch(UserNotFoundException unfe){
            logger.info("User "+userSignUpRequest.getEmailId()+" doesnot exist in system. Eligible to signup");
        }

        User newUser = new User();

        newUser.setUserName(userSignUpRequest.getEmailId());
        newUser.setPassword(userSignUpRequest.getPassword());
        newUser.setName(userSignUpRequest.getName());

        userDao.create(newUser);

        logger.info("User "+userSignUpRequest.getEmailId()+ " signedup Successfully!!!");
    }

    @Override
    public void signInUser(UserSignInRequest userSignInRequest){
        try{
            User user = userDao.findByUserName(userSignInRequest.getUserName());
            if(!userSignInRequest.getPassword().equals(user.getPassword())) {
                logger.error("Invalid Password provided for "+userSignInRequest.getUserName());
                throw new InvalidCredentialsException();
            }

        }catch(UserNotFoundException unfe){
            String errorMessage = unfe.getErrorMessage();
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
    }
}
