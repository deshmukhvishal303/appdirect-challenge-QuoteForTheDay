package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.UserDaoImpl;
import com.appdirect.quotes.db.model.entities.Session;
import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.dto.user.UserSignInRequest;
import com.appdirect.quotes.dto.user.UserSignInResponse;
import com.appdirect.quotes.dto.user.UserSignUpRequest;
import com.appdirect.quotes.exception.DuplicateUserException;
import com.appdirect.quotes.exception.InvalidCredentialsException;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.appdirect.quotes.service.api.AuthenticationService;
import com.appdirect.quotes.service.api.SessionService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDaoImpl userDaoImpl;
    private SessionService sessionService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    public AuthenticationServiceImpl(UserDaoImpl userDaoImpl, SessionService sessionService){
        this.userDaoImpl = userDaoImpl;
        this.sessionService = sessionService;
    }

    @Override
    public void signUpUser(UserSignUpRequest userSignUpRequest) {
        try {
            userDaoImpl.findByUserName(userSignUpRequest.getEmailId());

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

        userDaoImpl.create(newUser);

        logger.info("User "+userSignUpRequest.getEmailId()+ " signedup Successfully!!!");
    }

    @Override
    public UserSignInResponse signInUser(UserSignInRequest userSignInRequest){
        try{
            String userName = userSignInRequest.getUserName();
            User user = userDaoImpl.findByUserName(userName);
            if(!userSignInRequest.getPassword().equals(user.getPassword())) {
                logger.error("Invalid Password provided for "+userSignInRequest.getUserName());
                throw new InvalidCredentialsException();
            }

            String sessionId = sessionService.createSession(user);

            UserSignInResponse userSignInResponse = new UserSignInResponse();
            userSignInResponse.setSessionId(sessionId);

            return userSignInResponse;
        }catch(UserNotFoundException unfe){
            String errorMessage = unfe.getErrorMessage();
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }
    }

    @Override
    public void signOutUser(String sessionId) {
        Session session = sessionService.getSessionFromId(sessionId);
        if(session == null){
            String errorMessage = "Invalid Session Id";
            logger.error(errorMessage);
            throw new UserNotFoundException(errorMessage);
        }

        sessionService.deleteSession(session);
    }
}
