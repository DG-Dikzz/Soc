package com.dikzz.soc.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TwitterManager {
	
	private static final String REDIRECT_URL = "https://api.twitter.com/oauth/authorize";//"http://localhost:8080/Soc/api/test/twitterAuthorization";
	/*@Autowired
	private Facebook facebook;*/
	
	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocator;
	private TwitterConnectionFactory twitterСonnectionFactory;
	
	//private String accessCode;
	private Connection<Facebook> connection;
	//OAuth2Operations oauthOperations;
	
	public void setAccessCode(String accessCode) {
		//this.accessCode = accessCode;
		/*OAuth1Operations oauthOperations = twitterСonnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(accessCode, REDIRECT_URL, null);
		connection = facebookСonnectionFactory.createConnection(accessGrant);*/
	}

	@PostConstruct
	public void initConnections() {
		twitterСonnectionFactory = (TwitterConnectionFactory) connectionFactoryLocator.getConnectionFactory(Twitter.class);
	}
	
	/*public FacebookConnectionFactory getFacebookConnectionFactory() {
		return connectionFactory;
		//return facebook;
	}*/
	
	public FacebookProfile test() {
		FacebookProfile facebookProfile = connection.getApi().userOperations().getUserProfile("100007652822243");
		return facebookProfile;
	}
	
	public void updateStatus(String status) {
		connection.getApi().feedOperations().updateStatus(status);
	}
	
	public String getFullName() {
		UserProfile userProfile = connection.fetchUserProfile();
		return userProfile.getFirstName() + " " + userProfile.getLastName();
	}
	
	public String getAuthorizationUrl() {
		OAuth1Operations oauthOperations = twitterСonnectionFactory.getOAuthOperations();
		//OAuthToken requestToken = oauthOperations.fetchRequestToken(REDIRECT_URL, null);
		return oauthOperations.buildAuthorizeUrl(REDIRECT_URL, OAuth1Parameters.NONE);
		/*OAuth2Operations oauthOperations = facebookСonnectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(REDIRECT_URL);
		params.setScope("user_status, friends_status, export_stream, publish_actions, status_update");
		return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);*/
	}
	
	public UserProfile getUserProfile() {
		return connection.fetchUserProfile();
	}
}
