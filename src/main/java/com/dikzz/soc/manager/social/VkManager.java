package com.dikzz.soc.manager.social;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dikzz.soc.dto.vk.AccessTokenDto;
import com.dikzz.soc.dto.vk.ResponceDto;
import com.dikzz.soc.dto.vk.account.UserProfileDto;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

@Repository
public class VkManager {

	
	//@Qualifier("vkClientId")
	private String vkClientId;
	
	private AccessTokenDto accessTokenDto;

	private final static String CALLBACK_URL = "https://oauth.vk.com/blank.html";
	private final static String VK_SCOPE_ALL = "9999999";
	private final static String VK_AUTHORIZATION_DISPLAY = "popup";
	private final static String VK_API_VERSION = "5.14";
	private final static String AUTH_URL = "https://oauth.vk.com/authorize?client_id={0}&scope={1}&redirect_uri={2}&display={3}&v={4}&response_type=token";
	private final static String VK_METHOD_REQUEST_PATTERN = "https://api.vk.com/method/{0}?{1}{2}access_token={3}";
	private final static String ACCOUNT_GET_PROFILE_INFO = "account.getProfileInfo";

	public String getAuthorizationUrl() {
		return MessageFormat.format(AUTH_URL, "4236606", VK_SCOPE_ALL,
				CALLBACK_URL, VK_AUTHORIZATION_DISPLAY, VK_API_VERSION);
	}

	public void setAccessCode1(String accessCode) {
		// TODO set also other properties
		accessTokenDto = new AccessTokenDto();
		accessTokenDto.setAccessToken(accessCode);
	}

	public UserProfileDto getUserProfile() {

		String requestUrl = getVkMethodRequest(ACCOUNT_GET_PROFILE_INFO, "");
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(requestUrl);

			CloseableHttpResponse response = httpClient.execute(httpget);

			ObjectMapper objectMapper = new ObjectMapper();

			/*
			 * BufferedReader br = new BufferedReader( new
			 * InputStreamReader((response.getEntity().getContent())));
			 * StringBuilder r = new StringBuilder(); String output; while
			 * ((output = br.readLine()) != null) { r.append(output); }
			 */
			

			ResponceDto result = objectMapper.readValue(response.getEntity()
					.getContent(), ResponceDto.class);
			UserProfileDto userProfileDto = null;
			if (!result.hasError()) {
				userProfileDto = objectMapper.readValue(result.getResponce(),
						UserProfileDto.class);
			}
			return userProfileDto;
		} catch (ClientProtocolException e) {
			Throwables.propagate(e);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		return null;
	}

	private String getVkMethodRequest(String method, String parameters) {
		Preconditions.checkNotNull(accessTokenDto, "You are not authorized.");
		return MessageFormat.format(VK_METHOD_REQUEST_PATTERN, method,
				!Strings.isNullOrEmpty(parameters) ? parameters : "",
				!Strings.isNullOrEmpty(parameters) ? "&" : "",
				accessTokenDto.getAccessToken());
	}
	
	public String getClientId() {
		return vkClientId;
	}
}
