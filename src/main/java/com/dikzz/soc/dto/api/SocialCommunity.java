package com.dikzz.soc.dto.api;

import org.codehaus.jackson.annotate.JsonProperty;

import com.dikzz.soc.manager.social.CommunityType;

public interface SocialCommunity<T>{
	
	@JsonProperty("title")
	public String getTitle();
	@JsonProperty("description")
	public String getDescription();
	@JsonProperty("id")
	public String getId();
	
	@JsonProperty("communityType")
	public CommunityType getCommunityType();
	
	public void setAdaptableObject(T object);
}
