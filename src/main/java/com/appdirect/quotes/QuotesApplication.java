package com.appdirect.quotes;

import com.appdirect.quotes.service.api.AuthenticationService;
import com.appdirect.quotes.service.api.QuoteService;
import com.appdirect.quotes.service.api.SessionService;
import com.appdirect.quotes.service.api.UserService;
import com.appdirect.quotes.service.impl.AuthenticationServiceImpl;
import com.appdirect.quotes.service.impl.QuoteServiceImpl;
import com.appdirect.quotes.service.impl.SessionServiceImpl;
import com.appdirect.quotes.service.impl.UserServiceImpl;
import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mysql.cj.jdbc.MysqlDataSource;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.SessionFactory;
import org.reflections.Reflections;

import javax.persistence.Entity;
import javax.sql.DataSource;
import javax.ws.rs.Path;
import java.util.Set;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
public class QuotesApplication extends Application<QuotesConfiguration> {

    public static DataSource datasource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("jdbc:mysql://localhost/?"
                + "user=root");
        return ds;
    }

    public static void main(String args[]) throws Exception {
        new QuotesApplication().run(args);
    }

    private HibernateBundle<QuotesConfiguration> hibernateBundle = null;
    private final static String PACKAGE_URL = "com.appdirect.quotes";

    @Override
    public void initialize(Bootstrap<QuotesConfiguration> bootstrap) {
        Reflections reflections = new Reflections(PACKAGE_URL);
        ImmutableList<Class<?>> entities = ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
        hibernateBundle = new HibernateBundle<QuotesConfiguration>(entities, new SessionFactoryFactory()) {
            @Override
            public PooledDataSourceFactory getDataSourceFactory(QuotesConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new SwaggerBundle<QuotesConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(QuotesConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(QuotesConfiguration configuration, Environment environment) throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionFactory.class).toInstance(hibernateBundle.getSessionFactory());
                bind(UserService.class).to(UserServiceImpl.class).asEagerSingleton();
                bind(AuthenticationService.class).to(AuthenticationServiceImpl.class).asEagerSingleton();
                bind(QuoteService.class).to(QuoteServiceImpl.class).asEagerSingleton();
                bind(SessionService.class).to(SessionServiceImpl.class).asEagerSingleton();
            }
        });
        Reflections reflections = new Reflections(PACKAGE_URL);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Path.class);
        System.out.println(controllers);
        for(Class<?> controller : controllers)
            environment.jersey().register(injector.getInstance(controller));
    }

}