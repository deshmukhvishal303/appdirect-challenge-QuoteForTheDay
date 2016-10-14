package com.appdirect.quotes.exception;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class InvalidCredentialsException extends GenericQuotesException {
    public InvalidCredentialsException(){
        super("Invalid Password");
    }
}
