package com.ismail.Ecommerce.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;


    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
     // This method is used to configure the REST repository settings, such as exposing entity IDs and disabling certain HTTP methods.
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
         HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // Disable HTTP methods for Product: PUT, POST and DELETE


        // Disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disableHttpMethods(com.ismail.Ecommerce.entity.ProductCategory.class, config, unsupportedActions);
        disableHttpMethods(com.ismail.Ecommerce.entity.Product.class, config, unsupportedActions);
        disableHttpMethods(com.ismail.Ecommerce.entity.Country.class, config, unsupportedActions);
        disableHttpMethods(com.ismail.Ecommerce.entity.State.class, config, unsupportedActions);

        // Expose entity IDs in the JSON response for the Product and ProductCategory entities
        exposeIds(config);
    }

    private static void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        // Expose entity IDs in the JSON response for the Product and ProductCategory entities
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        // Create an array of the entity classes
        List<Class<?>> entityClasses = new ArrayList<>();

        // Add the entity classes to the list
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // expose the entity IDs for the array of entity/domain types
        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);


    }


}
