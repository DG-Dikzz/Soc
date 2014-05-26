package com.dikzz.soc.manager.social;

import com.dikzz.soc.dto.api.FacebookCommunity;
import com.dikzz.soc.dto.api.SocialCommunity;
import com.dikzz.soc.dto.api.TwitterCommunity;
import com.dikzz.soc.dto.api.VkCommunity;
import com.google.common.base.Throwables;

public enum CommunityType {
	VK(VkCommunity.class),
	FACEBOOK(FacebookCommunity.class),
	TWITTER(TwitterCommunity.class);

	private Class<? extends SocialCommunity<?>> clazz;

	private CommunityType(Class<? extends SocialCommunity<?>> clazz) {
		this.clazz = clazz;
	}

	public <T> SocialCommunity<T> getSocialCommunityInstance(T object) {
		try {
			@SuppressWarnings("unchecked")
			SocialCommunity<T> instance = (SocialCommunity<T>) this.clazz
					.newInstance();
			instance.setAdaptableObject(object);
			return instance;
		} catch (InstantiationException e) {
			Throwables.propagate(e);
		} catch (IllegalAccessException e) {
			Throwables.propagate(e);
		}
		return null;
	}
}