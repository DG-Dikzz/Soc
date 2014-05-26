package com.dikzz.soc.manager.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Repository;

import com.dikzz.soc.dto.api.SocialCommunity;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Repository
public class TwitterManager implements SocialManager {

	private static final String REDIRECT_URL = "http://localhost:8080/Soc/api/twitter";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocatorSocial;
	private TwitterConnectionFactory twitterСonnectionFactory;
	private Connection<Twitter> connection;

	private OAuthToken oAuthToken;
	
	/**
	 * HACK Used for defination last page result. 
	 * For some reasone in twitter api even you request unexisting page it returns last existing page.
	 */
	private Set<Long> lastUsersResponseSnapshot = Sets.newHashSet();

	@PostConstruct
	public void initConnections() {
		twitterСonnectionFactory = (TwitterConnectionFactory) connectionFactoryLocatorSocial
				.getConnectionFactory(Twitter.class);
	}

	public String getAuthorizationUrl() {
		OAuth1Operations oauthOperations = twitterСonnectionFactory
				.getOAuthOperations();
		oAuthToken = oauthOperations.fetchRequestToken(REDIRECT_URL, null);
		OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();
		oAuth1Parameters.setCallbackUrl(REDIRECT_URL);
		return oauthOperations.buildAuthorizeUrl(oAuthToken.getValue(),
				oAuth1Parameters);
	}

	public void setAccessCode(String accessCode, String oAuthVerifier) {
		OAuth1Operations oauthOperations = twitterСonnectionFactory
				.getOAuthOperations();
		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(
				oAuthToken, oAuthVerifier);
		OAuthToken accessToken = oauthOperations.exchangeForAccessToken(
				authorizedRequestToken, null);
		connection = twitterСonnectionFactory.createConnection(accessToken);
	}

	private List<TwitterProfile> getProfiles(String query, Integer page) {
		Twitter twitter = connection.getApi();
		List<TwitterProfile> profiles = twitter.userOperations()
				.searchForUsers(Strings.nullToEmpty(query), page, PAGE_COUNT);
		return profiles;
	}

	@Override
	public List<SocialCommunity<?>> getCommunities(String query, final Integer page) {
		List<TwitterProfile> groups = getProfiles(query, page);
		List<Long> ids = new ArrayList<Long>();
		List<SocialCommunity<?>> socialCommunities = Lists.transform(groups,
				new Function<TwitterProfile, SocialCommunity<?>>() {
					@Override
					public SocialCommunity<?> apply(TwitterProfile object) {
						return CommunityType.TWITTER
								.getSocialCommunityInstance(object);
					}
				});
		
		List<SocialCommunity<?>> result = Lists.newArrayList();
		for(SocialCommunity<?> socialCommunity : socialCommunities) {
			if(!(lastUsersResponseSnapshot.contains(Long.parseLong(socialCommunity.getId())) && page > 1)) {
				result.add(socialCommunity);
				ids.add(Long.parseLong(socialCommunity.getId()));
			}
		}
		
		
		if(!ids.isEmpty()) {
			lastUsersResponseSnapshot = Sets.newHashSet();
			lastUsersResponseSnapshot.addAll(ids);
		}
		return result;
	}
}
