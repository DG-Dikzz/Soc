package com.dikzz.soc;

import java.util.Map;

import com.dikzz.soc.manager.social.CommunityType;
import com.dikzz.soc.manager.social.SocialManager;
import com.google.common.collect.Maps;

public class CommunityManagerConfiguration {
	private Map<CommunityType, ? extends SocialManager> socialManagers = Maps
			.newHashMap();

	public Map<CommunityType, ? extends SocialManager> getSocialManagers() {
		return socialManagers;
	}

	public void setSocialManagers(
			Map<CommunityType, ? extends SocialManager> socialManagers) {
		this.socialManagers = socialManagers;
	}
	
	public SocialManager getSocialManager(CommunityType communityType) {
		return socialManagers.get(communityType);
	}
}
