package com.appdirect.quotes.db.dao;

import com.appdirect.quotes.db.model.entities.User;
import com.appdirect.quotes.exception.UserNotFoundException;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class UserDao extends AbstractDAO<User> {

    @Inject
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User findById(Long id) {
        return get(id);
    }

    public long create(User user) {
        return persist(user).getId();
    }

    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public User findByUserName(String userName){
        Query query = currentSession().createQuery("FROM "+User.class.getName() + " u WHERE u.userName=:userName");
        query.setParameter("userName", userName);
        List<User> userList = query.list();

        if(userList == null || userList.isEmpty()){
            String errorMessage = "User "+userName+ " not found";
            throw new UserNotFoundException(errorMessage);
        }

        return userList.get(0);
    }
}
