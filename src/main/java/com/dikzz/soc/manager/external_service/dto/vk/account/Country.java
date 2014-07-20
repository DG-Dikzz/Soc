package com.dikzz.soc.manager.external_service.dto.vk.account;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
	
	@JsonProperty(value = "cid")
	private Integer id;
	
	@JsonProperty(value = "title")
	private String title;
	
	public Country() {
	}
	
	public Country(Integer id, String title) {
		this.id = id;
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
