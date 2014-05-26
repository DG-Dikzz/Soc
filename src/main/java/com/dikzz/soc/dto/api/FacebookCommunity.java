package com.dikzz.soc.dto.api;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.social.facebook.api.Group;

import com.dikzz.soc.manager.social.CommunityType;

public class FacebookCommunity implements SocialCommunity<Group> {

	@JsonIgnore
	private Group group;

	public FacebookCommunity() {

	}

	@Override
	public String getTitle() {
		return group.getName();
	}

	@Override
	public String getDescription() {
		return group.getDescription();
	}

	@Override
	public CommunityType getCommunityType() {
		return CommunityType.FACEBOOK;
	}

	public Group getGroup() {
		return group;
	}

	@Override
	public void setAdaptableObject(Group object) {
		group = object;
	}

	@Override
	public String getId() {
		return group.getId();
	}

}
