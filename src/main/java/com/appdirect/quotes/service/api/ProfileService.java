package com.appdirect.quotes.service.api;

import com.appdirect.quotes.db.model.entities.Profile;
import com.appdirect.quotes.service.impl.ProfileServiceImpl;
import com.google.inject.ImplementedBy;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
@ImplementedBy(ProfileServiceImpl.class)
public interface ProfileService {
    Profile getUserByOpenId(String openId);
    void create(Profile profile);
    void delete(Profile profile);
}
