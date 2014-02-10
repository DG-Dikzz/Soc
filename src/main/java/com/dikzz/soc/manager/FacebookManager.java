package com.dikzz.soc.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Repository;

@Repository
public class FacebookManager {
	
	private static final String REDIRECT_URL = "http://localhost:8080/Soc/api/test/facebookAuthorization";
	/*@Autowired
	private Facebook facebook;*/
	
	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocator;
	private FacebookConnectionFactory connectionFactory;
	//private String accessCode;
	private Connection<Facebook> connection;
	//OAuth2Operations oauthOperations;
	
	public void setAccessCode(String accessCode) {
		//this.accessCode = accessCode;
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(accessCode, REDIRECT_URL, null);
		connection = connectionFactory.createConnection(accessGrant);
	}

	@PostConstruct
	public void initConnections() {
		connectionFactory = (FacebookConnectionFactory) connectionFactoryLocator.getConnectionFactory(Facebook.class);
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
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(REDIRECT_URL);
		params.setScope("user_status, friends_status, export_stream, publish_actions, status_update");
		return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
	}
	
	public UserProfile getUserProfile() {
		return connection.fetchUserProfile();
	}
}
