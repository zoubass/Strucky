package cz.zoubelu.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zoubas
 */
//@org.springframework.context.annotation.Configuration
//@Profile("production")
public class AppConfiguration {

//    @Bean
    public PropertyPlaceholderConfigurer propertyPlaceHolder() {
        PropertyPlaceholderConfigurer propertyPlaceHolder = new PropertyPlaceholderConfigurer();
        propertyPlaceHolder.setLocation(new ClassPathResource("oracle_database.properties"));
        propertyPlaceHolder.setIgnoreResourceNotFound(true);
        return propertyPlaceHolder;
    }
//
//    private BasicDataSource createH2DataSource() {
//
//        return null;
//    }
}
