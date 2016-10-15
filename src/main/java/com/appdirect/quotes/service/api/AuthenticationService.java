package com.appdirect.quotes.service.api;

import com.appdirect.quotes.dto.user.UserSignInRequest;
import com.appdirect.quotes.dto.user.UserSignInResponse;
import com.appdirect.quotes.dto.user.UserSignUpRequest;
import com.appdirect.quotes.service.impl.AuthenticationServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@ImplementedBy(AuthenticationServiceImpl.class)
public interface AuthenticationService {
    void signUpUser(UserSignUpRequest userSignUpRequest);
    UserSignInResponse signInUser(UserSignInRequest userSignInRequest);
    void signOutUser(String sessionId);
}
