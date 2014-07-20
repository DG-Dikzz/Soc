package com.dikzz.soc.manager.social;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Repository;

import com.dikzz.soc.dto.api.SocialAccessStatus;
import com.dikzz.soc.dto.api.SocialAccessStatus.SocialAccessState;
import com.dikzz.soc.dto.api.SocialCommunity;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Repository
public class FacebookManager implements SocialManager {

	private static final String REDIRECT_URL = "http://localhost:8080/Soc/api/facebook";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocatorSocial;
	private FacebookConnectionFactory connectionFactory;
	private Connection<Facebook> connection;

	@PostConstruct
	public void initConnections() {
		connectionFactory = (FacebookConnectionFactory) connectionFactoryLocatorSocial
				.getConnectionFactory(Facebook.class);
	}

	public void setAccessCode(String accessCode) {
		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(accessCode,
				REDIRECT_URL, null);
		connection = connectionFactory.createConnection(accessGrant);
	}

	public String getAuthorizationUrl() {
		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(REDIRECT_URL);
		params.setScope("user_status, friends_status, export_stream, publish_actions, status_update");
		return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE,
				params);
	}

	private List<Group> getGroups(String query, Integer page) {
		Facebook facebook = connection.getApi();
		List<Group> groups = facebook.groupOperations().search(
					Strings.nullToEmpty(query), PAGE_COUNT * page,
					PAGE_COUNT);
		return groups;
	}

	@Override
	public List<SocialCommunity<?>> getCommunities(String query,
			Integer page) {
		List<Group> groups = getGroups(query, page);

		return Lists.transform(groups,
				new Function<Group, SocialCommunity<?>>() {

					@Override
					public SocialCommunity<?> apply(Group object) {
						return CommunityType.FACEBOOK
								.getSocialCommunityInstance(object);

					}
				});
	}

	@Override
	public SocialAccessStatus getAccessStatus() {
		SocialAccessState state = null;
		if (connection != null && connection.test()) {
			state = SocialAccessState.ACCESSIBLE;
		} else {
			state = SocialAccessState.INACCESSIBLE;
		}
		return new SocialAccessStatus(state, CommunityType.FACEBOOK);
	}
}