package com.dikzz.soc.dto.api;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.social.twitter.api.TwitterProfile;

import com.dikzz.soc.manager.social.CommunityType;

public class TwitterCommunity implements SocialCommunity<TwitterProfile> {

	@JsonIgnore
	private TwitterProfile twitterProfile;

	public TwitterCommunity() {

	}

	@Override
	public String getTitle() {
		return twitterProfile.getName();
	}

	@Override
	public String getDescription() {
		return twitterProfile.getDescription();
	}

	@Override
	public CommunityType getCommunityType() {
		return CommunityType.TWITTER;
	}

	public TwitterProfile getTwitterProfile() {
		return twitterProfile;
	}

	@Override
	public void setAdaptableObject(TwitterProfile object) {
		twitterProfile = object;

	}

	@Override
	public String getId() {
		return String.valueOf(twitterProfile.getId());
	}

	@JsonProperty("screen_name")
	public String getScreenName() {
		return twitterProfile.getScreenName();
	}

}
