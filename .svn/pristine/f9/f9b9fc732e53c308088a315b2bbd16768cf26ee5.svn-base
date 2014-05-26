package com.dikzz.soc.dto.vk.account;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDto {

	public static enum Gender {
		NONE, FEMALE, MALE
	};

	public static enum Relation {
		NONE, SINGLE, IN_RELATION, ENGAGED, MARRIED, COMPLICATED, IN_SEARCH, FALLEN_IN_LOVE
	};

	public static enum BirthDateVisibility {
		UNVISIBLE, SHOW_ALL, SHOW_MONTH_AND_DAY
	};
	
	@JsonProperty(value = "first_name")
	private String firstName;
	
	@JsonProperty(value = "last_name")
	private String lastName;
	
	@JsonProperty(value = "maiden_name")
	private String maideName;
	
	@JsonProperty(value = "sex")
	private Gender gender;
	
	@JsonProperty(value = "relation")
	private Relation relation;
	
	@JsonProperty(value = "relation_partner")
	private RelationPartner relationPartner;
	
	@JsonProperty(value = "bdate")
	private String birthDate;
	
	@JsonProperty(value = "bdate_visibility")
	private BirthDateVisibility birthDateVisibility;
	
	@JsonProperty(value = "home_town")
	private String homeTown;
	
	@JsonProperty(value = "access_token")
	private Country country;
	
	@JsonProperty(value = "city")
	private City city;
	// TODO implement name_request

	public UserProfileDto() {
		
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

	public String getMaideName() {
		return maideName;
	}

	public void setMaideName(String maideName) {
		this.maideName = maideName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public RelationPartner getRelationPartner() {
		return relationPartner;
	}

	public void setRelationPartner(RelationPartner relationPartner) {
		this.relationPartner = relationPartner;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public BirthDateVisibility getBirthDateVisibility() {
		return birthDateVisibility;
	}

	public void setBirthDateVisibility(BirthDateVisibility birthDateVisibility) {
		this.birthDateVisibility = birthDateVisibility;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
