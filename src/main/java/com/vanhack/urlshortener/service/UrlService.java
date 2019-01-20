package com.vanhack.urlshortener.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vanhack.urlshortener.model.Url;
import com.vanhack.urlshortener.repository.UrlRepository;

/**
* Service used by url controller.
*
* @author  Antonio Theodosio
* @version 1.0
* @since   2019-01-20 
*/
@Service
public class UrlService {

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ0123456789";

	//this is the length of the id
	private int length = 5;
	
	@Autowired
	private UrlRepository urlRepository;
	
	/**
	 * Method to create a random id
	 * @return String id
	 */
	public String getNewId() {

		Url findOne = new Url();
		StringBuilder id = null;
		int counter=0;
		do {
			
			//try to find 10 times a new unused id, otherwise it will increase the length of the id
			if(counter==10) { 
				length++;
				counter=0;
			}
			
			//build a random id
			id = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				id.append(CHARS.charAt(random.nextInt(CHARS.length())));
			}
			
			//search this id in database
			findOne = urlRepository.findOne(id.toString());
			counter++;
			
		} while (findOne!=null); //if the id is used, try to find another one.
		
	    return id.toString();

	}
}
