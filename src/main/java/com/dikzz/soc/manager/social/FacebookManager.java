package com.dikzz.soc.manager.social;

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

	public FacebookProfile test() {
		FacebookProfile facebookProfile = connection.getApi().userOperations()
				.getUserProfile("100007652822243");
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
		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(REDIRECT_URL);
		params.setScope("user_status, friends_status, export_stream, publish_actions, status_update");
		return oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE,
				params);
	}

	public UserProfile getUserProfile() {
		return connection.fetchUserProfile();
	}
}
