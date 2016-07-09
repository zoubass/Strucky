package cz.zoubelu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class DataSourceProductionConfig implements DataSource {

    //TODO: zmenit odkaz na property
    @Value("${h2.url}")
    private String url;

    @Value("${h2.driver}")
    private String driver;

    @Value("${h2.username}")
    private String username;

    @Value("${h2.password}")
    private String password;

    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource bd = new BasicDataSource();
//        bd.setDriverClassName(driver);
//        bd.setUrl(url);
//        bd.setUsername(username);
//        bd.setPassword(password);
//        bd.setInitialSize(5);
//        bd.setMaxActive(10);
//        bd.setPoolPreparedStatements(true);
        return bd;
    }
}
