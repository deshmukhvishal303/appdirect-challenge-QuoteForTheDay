package com.appdirect.quotes.exception;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class DuplicateUserException extends GenericQuotesException {
    public DuplicateUserException(String errorMessage){
        super(errorMessage);
    }
}
