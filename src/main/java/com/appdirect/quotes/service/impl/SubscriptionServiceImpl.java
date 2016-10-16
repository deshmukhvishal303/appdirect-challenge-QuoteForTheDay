package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.model.entities.Profile;
import com.appdirect.quotes.dto.subscription.Creator;
import com.appdirect.quotes.dto.subscription.SubscriptionOrderEvent;
import com.appdirect.quotes.dto.auth.OAuthSignature;
import com.appdirect.quotes.dto.event.EventResponse;
import com.appdirect.quotes.exception.FieldNotFoundException;
import com.appdirect.quotes.exception.SubscriptionConflictException;
import com.appdirect.quotes.service.api.ProfileService;
import com.appdirect.quotes.service.api.SubscriptionService;
import com.google.inject.Inject;

import javax.ws.rs.BadRequestException;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
public class SubscriptionServiceImpl implements SubscriptionService {

    private ProfileService profileService;

    @Inject
    public SubscriptionServiceImpl(ProfileService profileService){
        this.profileService = profileService;
    }

    @Override
    public EventResponse createSubscription(String url, OAuthSignature oAuthSignature) {
        String inputSecret = oAuthSignature.getSecret();
        String inputToken = oAuthSignature.getAccessToken();

        if(!"secret".equals(inputSecret) || !"dummy".equals(inputToken))
            throw new BadRequestException();

        //ToDo Get SubscriptionEvent from AppDirect API via jersey Client
        SubscriptionOrderEvent subscriptionOrderEvent = new SubscriptionOrderEvent();
        Creator creator = subscriptionOrderEvent.getCreator();

        if(creator == null){
            throw new FieldNotFoundException();
        }

        Profile profile = profileService.getUserByOpenId(creator.getOpenId());

        if(profile != null){
            throw new SubscriptionConflictException();
        }

        profile = new Profile();
        profile.setFirstName(creator.getFirstName());
        profile.setLastName(creator.getLastName());
        profile.setEmail(creator.getEmail());
        profile.setOpenId(creator.getOpenId());

        profileService.create(profile);

        return new EventResponse();
    }

    @Override
    public void cancelSubscription(String url, OAuthSignature oAuthSignature) {
        String inputSecret = oAuthSignature.getSecret();
        String inputToken = oAuthSignature.getAccessToken();

        if(!"secret".equals(inputSecret) || !"dummy".equals(inputToken))
            throw new BadRequestException();

        //ToDo Get SubscriptionEvent from AppDirect API via jersey Client
        SubscriptionOrderEvent subscriptionOrderEvent = new SubscriptionOrderEvent();
        Creator creator = subscriptionOrderEvent.getCreator();

        if(creator == null){
            throw new FieldNotFoundException();
        }

        Profile profile = profileService.getUserByOpenId(creator.getOpenId());

        profileService.delete(profile);
    }
}
