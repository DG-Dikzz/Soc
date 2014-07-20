package com.dikzz.soc.dto.api;

import com.dikzz.soc.manager.social.CommunityType;

public final class SocialAccessStatus {
	public static enum SocialAccessState {
		INACCESSIBLE, ACCESSIBLE
	}
	
	private final SocialAccessState state;
	private final CommunityType communityType;
	
	public SocialAccessStatus(SocialAccessState state, CommunityType communityType) {
		this.state = state;
		this.communityType = communityType;
	}

	public SocialAccessState getState() {
		return state;
	}

	public CommunityType getCommunityType() {
		return communityType;
	}
}
