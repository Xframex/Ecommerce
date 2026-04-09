package com.ismail.Ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

     @Override
     // This method is used to configure the REST repository settings, such as exposing entity IDs and disabling certain HTTP methods.
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
         HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // Disable HTTP methods for Product: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(com.ismail.Ecommerce.entity.Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

        // Disable HTTP methods for ProductCategory: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(com.ismail.Ecommerce.entity.ProductCategory.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));

    }


}
