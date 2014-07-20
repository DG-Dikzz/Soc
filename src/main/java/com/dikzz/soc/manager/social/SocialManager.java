package com.dikzz.soc.manager.social;

import java.util.List;

import com.dikzz.soc.dto.api.SocialAccessStatus;
import com.dikzz.soc.dto.api.SocialCommunity;

public interface SocialManager {
	public static final Integer PAGE_COUNT = 20;
	public List<SocialCommunity<?>> getCommunities(String query, Integer page);
	public SocialAccessStatus getAccessStatus();
}
