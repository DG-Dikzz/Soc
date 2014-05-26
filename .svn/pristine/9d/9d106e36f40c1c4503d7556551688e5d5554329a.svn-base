package com.dikzz.soc.dto.vk;

import java.text.MessageFormat;

import org.codehaus.jackson.annotate.JsonProperty;

public class AccessTokenDto {
	@JsonProperty(value = "access_token")
	private String accessToken;
	@JsonProperty(value = "expires_in")
	private String expiresIn;
	@JsonProperty(value = "user_id")
	private String userId;

	public AccessTokenDto() {

	}

	public AccessTokenDto(String accessToken, String expiresIn, String userId) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return MessageFormat.format(
				"{0}: accessToken = {1}, expiresIn = {2}, userId = {3}",
				this.getClass().getName(), this.accessToken,
				this.expiresIn, this.userId);
	}
}