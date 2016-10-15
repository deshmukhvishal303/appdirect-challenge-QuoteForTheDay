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
    void createQuote(String sessionId, QuoteCreateRequest quoteCreateRequest);
    void updateQuote(String sessionId, QuoteUpdateRequest quoteUpdateRequest);
    QuoteResponse getQuote(Long id, String sessionId);
    QuoteResponse getQuoteForUser(String sessionId);
    void deleteQuote(Long quoteId, String sessionId);
}
