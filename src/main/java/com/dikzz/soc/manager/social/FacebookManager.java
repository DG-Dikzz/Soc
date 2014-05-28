package com.dikzz.soc.manager.social;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.api.Group;
import org.springframework.stereotype.Repository;

import com.dikzz.soc.dto.api.SocialCommunity;
import com.dikzz.soc.dto.vk.FacebookAccessTokenDto;
import com.dikzz.soc.request.facebook.FacebookAccessTokenRequest;
import com.dikzz.soc.request.facebook.FacebookAccessTokenRequest.ResponseType;
import com.dikzz.soc.request.facebook.FacebookAccessTokenRequest.ScopeValue;
import com.dikzz.soc.utils.RestUtils;
import com.dikzz.soc.utils.RestUtils.ResponseHandler;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;

@Repository
public class FacebookManager implements SocialManager {

	@Value("${social.facebook.clientId}")
	private String clientId;

	@Value("${social.facebook.clientSecret}")
	private String clientSecret;
	
	private FacebookAccessTokenDto facebookAccessTokenDto;

	private static final String REDIRECT_URL = "http://localhost:8080/Soc/api/facebook";

	private static final String API_URL = "https://graph.facebook.com/";

	public static final String AUTHORIZATION_PATH = "oauth/authorize?";
	public static final String ACCESS_TOKEN_PATH = "oauth/access_token?";

	@Autowired
	private ConnectionFactoryRegistry connectionFactoryLocatorSocial;

	public void setAccessCode(String accessCode) {
		String url = MessageFormat.format("{0}{1}{2}", API_URL,
				ACCESS_TOKEN_PATH,
				FacebookAccessTokenRequest.build(clientId, REDIRECT_URL)
						.setClientSecret(clientSecret).setCode(accessCode)
						.getRequest().getParameters());
		this.facebookAccessTokenDto = RestUtils.request(url, new ResponseHandler<FacebookAccessTokenDto>() {

			@Override
			public FacebookAccessTokenDto handle(InputStream inputStream)
					throws JsonParseException, JsonMappingException,
					IOException {
				//TODO Regex
				String response = CharStreams.toString(new InputStreamReader(inputStream, "UTF-8"));
				final String accessToken = "access_token=";
				final String expires = "expires=";
				int indexOfAccessToken = response.indexOf(accessToken);
				int indexOfAmp = response.indexOf("&");
				int indexOfExpires = response.indexOf(expires);
				return new FacebookAccessTokenDto(response.substring(indexOfAccessToken + accessToken.length(), indexOfAmp), response.substring(indexOfExpires + expires.length(), response.length()));
			}
		});
	}
	
	
	public String getAuthorizationUrl() {
		return MessageFormat.format(
				"{0}{1}{2}",
				API_URL,
				AUTHORIZATION_PATH,
				FacebookAccessTokenRequest
						.build(clientId, REDIRECT_URL)
						.setResponseType(ResponseType.CODE)
						.setScope(ScopeValue.EXPORT_STREAM,
								ScopeValue.FRIENDS_STATUS,
								ScopeValue.PUBLISH_ACTIONS,
								ScopeValue.STATUS_UPDATE,
								ScopeValue.USER_STATUS).getRequest()
						.getParameters());
	}

	private List<Group> getGroups(String query, Integer page) {
		/*
		 * Facebook facebook = connection.getApi(); List<Group> groups =
		 * facebook.groupOperations().search( Strings.nullToEmpty(query),
		 * PAGE_COUNT * page, PAGE_COUNT);
		 */
		return null;// groups;
	}

	@Override
	public List<SocialCommunity<?>> getCommunities(String query, Integer page) {
		List<Group> groups = new ArrayList<Group>();// getGroups(query, page);

		return Lists.transform(groups,
				new Function<Group, SocialCommunity<?>>() {

					@Override
					public SocialCommunity<?> apply(Group object) {
						return CommunityType.FACEBOOK
								.getSocialCommunityInstance(object);

					}
				});
	}
	
	public static void main(String[] args) {
		String temp = "access_token=CAALfgSXdnxABAKLwD8UJAB4RMjYOozrPqzhNpVRoO4jZCaaOXzDhpvcfJ3W3vuIzy0N25ZC4FGz15cje7ZCigBZCFOQq0N469c4ZBGj6a7SHFCF720cABc9uhFyVIOiTZB5SO3uwIAZB6a7lIwmynDIkvmkHwgRwRXYY80a3a4wBQ1CsFDbo1U8NYQUzK0X8dIZD&expires=5106717";
		
		final String accessToken = "access_token=";
		final String expires = "expires=";
		int indexOfAccessToken = temp.indexOf(accessToken);
		int indexOfAmp = temp.indexOf("&");
		int indexOfExpires = temp.indexOf(expires);
		System.out.println(temp.substring(indexOfAccessToken + accessToken.length(), indexOfAmp));
		System.out.println(temp.substring(indexOfExpires + expires.length(), temp.length()));
	}
}