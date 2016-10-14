package com.appdirect.quotes.exception;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class UserNotFoundException extends GenericQuotesException {
    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
