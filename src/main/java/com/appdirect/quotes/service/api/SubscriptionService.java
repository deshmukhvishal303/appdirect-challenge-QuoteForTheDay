package com.appdirect.quotes.service.api;

import com.appdirect.quotes.dto.auth.OAuthSignature;
import com.appdirect.quotes.dto.event.EventResponse;
import com.appdirect.quotes.service.impl.SubscriptionServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
@ImplementedBy(SubscriptionServiceImpl.class)
public interface SubscriptionService {
    EventResponse createSubscription(String url, OAuthSignature oAuthSignature);
    void cancelSubscription(String url, OAuthSignature oAuthSignature);
}
