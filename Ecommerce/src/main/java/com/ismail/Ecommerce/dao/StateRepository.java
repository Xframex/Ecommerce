package com.ismail.Ecommerce.dao;

import com.ismail.Ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "states", path = "states")
public interface StateRepository extends JpaRepository<State, Integer> {

    // TODO --> add method to find states by country code
    List<State> findByCountryCode(@Param("code") String code);
}
