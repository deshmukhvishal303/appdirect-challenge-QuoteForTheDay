package com.appdirect.quotes.service.api;

import com.appdirect.quotes.dto.quote.QuoteCreateRequest;
import com.appdirect.quotes.dto.quote.QuoteResponse;
import com.appdirect.quotes.dto.quote.QuoteUpdateRequest;
import com.appdirect.quotes.service.impl.QuoteServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 14/10/16.
 */
@ImplementedBy(QuoteServiceImpl.class)
public interface QuoteService {
    void createQuote(QuoteCreateRequest quoteCreateRequest);
    void updateQuote(QuoteUpdateRequest quoteUpdateRequest);
    QuoteResponse getQuote(Long id);
    QuoteResponse getQuoteForUser(String userName);
    void deleteQuote(Long quoteId);
}
