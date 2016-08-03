package cz.zoubelu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zoubas on 26.6.16.
 */
//@Configuration
public class DataSource {

    @Value("${url}")
    private String url;

    @Value("${driver}")
    private String driver;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

/*
    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource bd = new BasicDataSource();
        bd.setDriverClassName(driver);
        bd.setUrl(url);
        bd.setUsername(username);
        bd.setPassword(password);
        bd.setInitialSize(5);
        bd.setMaxActive(10);
        bd.setPoolPreparedStatements(true);
        return bd;
    }*/
}
