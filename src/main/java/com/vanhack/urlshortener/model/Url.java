package com.vanhack.urlshortener.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* Class model to persiste url information.
*
* @author  Antonio Theodosio
* @version 1.0
* @since   2019-01-20 
*/
@Entity
@Table
public class Url {
    
	@Id
	@Column
	private String id;

	@Column
	private String longUrl;
	
	@Column
	private String status;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column
	private Timestamp created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
}
