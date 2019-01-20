package com.vanhack.urlshortener.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vanhack.urlshortener.model.Url;

/**
* Interface used by hibernate to persiste url information.
*
* @author  Antonio Theodosio
* @version 1.0
* @since   2019-01-20 
*/
public interface UrlRepository extends CrudRepository<Url, String> {
	List<Url> findAll();
}
