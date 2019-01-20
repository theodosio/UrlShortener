package com.vanhack.urlshortener.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.urlshortener.model.Url;
import com.vanhack.urlshortener.repository.UrlRepository;
import com.vanhack.urlshortener.service.UrlService;

/**
* Rest Controller to manage Url shotener API.
*
* @author  Antonio Theodosio
* @version 1.0
* @since   2019-01-20 
*/
@RestController
public class UrlShortenerApiRestController {
    
	private UrlRepository urlRepository;
	
	private UrlService urlService;
	
	@Autowired
	public UrlShortenerApiRestController(UrlRepository urlRepository, UrlService urlService) {
		this.urlRepository = urlRepository;
		this.urlService = urlService;
	}

	/**
	 * Api Rest to get all urls.
	 * @param request
	 * @return List of all short urls in database
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(HttpServletRequest request) {
        try {    		
        	List<Url> UrlList = urlRepository.findAll();
            return new ResponseEntity<>(UrlList, HttpStatus.OK);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
    
	/**
	 * Api Rest to create new short url from a long url.
	 * Example: { "longUrl":"https://www.google.com/" }
	 * @param url
	 * @param request
	 * @return the information of the created url.
	 */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody Url url, HttpServletRequest request) {

        try {    		
            if ((url.getLongUrl() != null)) {
            	url.setId(urlService.getNewId());
            	url.setCreated_at(new Timestamp(System.currentTimeMillis()));
            	url.setStatus("OK");
            	urlRepository.save(url);
                return new ResponseEntity<>(url, HttpStatus.CREATED);
            }

	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
    }
    
    /**
     * Api to redirect from short url to destination url.
     * Example of short url: http://localhost/ek7kp
     * @param id
     * @param request
     * @return response http that redirect to the registered url
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> redirect(@PathVariable("id") String id, HttpServletRequest request) {
        try {
        	Url url = urlRepository.findOne(id);
        	if(url!=null&&url.getStatus().equals("OK")) {
        		
        	    HttpHeaders headers = new HttpHeaders();
        	    headers.add("Location", url.getLongUrl());
        	    
				return new ResponseEntity<>(headers, HttpStatus.TEMPORARY_REDIRECT);    
				
        	} else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	/**
	 * Api Rest to remove short url.
	 * @param id
	 * @param request
	 * @return none
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> remove(@PathVariable("id") String id, HttpServletRequest request) {
        try {
        	Url url = urlRepository.findOne(id);
        	if(url!=null&&url.getStatus().equals("OK")) {
        		
        		url.setStatus("REMOVED");
        		urlRepository.save(url);
        	    
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);     
				
        	} else {
        		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        	}
        	
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
