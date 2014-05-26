package com.dikzz.soc.dto.vk.account;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationPartner {

	@JsonProperty(value = "uid")
	private Integer id;
	
	@JsonProperty(value = "first_name")
	private String firstName;
	
	@JsonProperty(value = "last_name")
	private String lastName;
	
	public RelationPartner() {
	}
	
	public RelationPartner(Integer id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
