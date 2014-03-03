package com.dikzz.soc.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterManager {
	
	private static final String REDIRECT_URL = "http://localhost:8080/Soc/api/test/twitterAuthorization";
	
	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocator;
	private TwitterConnectionFactory twitterСonnectionFactory;
	private Connection<Twitter> connection;
	
	private OAuthToken oAuthToken;
	
	@PostConstruct
	public void initConnections() {
		twitterСonnectionFactory = (TwitterConnectionFactory) connectionFactoryLocator.getConnectionFactory(Twitter.class);
	}
	
	public String getFullName() {
		UserProfile userProfile = connection.fetchUserProfile();
		return userProfile.getFirstName() + " " + userProfile.getLastName();
	}
	
	public String getAuthorizationUrl() {
		OAuth1Operations oauthOperations = twitterСonnectionFactory.getOAuthOperations();
		oAuthToken = oauthOperations.fetchRequestToken(REDIRECT_URL, null);
		OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();
		oAuth1Parameters.setCallbackUrl(REDIRECT_URL);
		return oauthOperations.buildAuthorizeUrl(oAuthToken.getValue(), oAuth1Parameters);
	}
	
	public void setAccessCode(String accessCode, String oAuthVerifier) {
		OAuth1Operations oauthOperations = twitterСonnectionFactory.getOAuthOperations();
		AuthorizedRequestToken authorizedRequestToken = new AuthorizedRequestToken(oAuthToken, oAuthVerifier);
		OAuthToken accessToken = oauthOperations.exchangeForAccessToken(authorizedRequestToken, null);
		connection = twitterСonnectionFactory.createConnection(accessToken);
	}
}
