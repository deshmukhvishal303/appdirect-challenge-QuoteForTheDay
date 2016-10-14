package com.appdirect.quotes.exception;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class GenericQuotesException extends RuntimeException {
    private String errorMessage;

    public GenericQuotesException(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
