package com.appdirect.quotes.service.impl;

import com.appdirect.quotes.db.dao.impl.ProfileDaoImpl;
import com.appdirect.quotes.db.model.entities.Profile;
import com.appdirect.quotes.service.api.ProfileService;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
public class ProfileServiceImpl implements ProfileService {
    ProfileDaoImpl profileDao;

    @Inject
    public ProfileServiceImpl(ProfileDaoImpl profileDao){
        this.profileDao = profileDao;
    }

    @Override
    public Profile getUserByOpenId(String openId) {
        List<Profile> profileList = profileDao.findByOpenId(openId);

        if(profileList == null || profileList.isEmpty())
            return null;

        return profileList.get(0);
    }

    @Override
    public void create(Profile profile) {
        profileDao.create(profile);
    }

    @Override
    public void delete(Profile profile) {
        profileDao.delete(profile);
    }
}
