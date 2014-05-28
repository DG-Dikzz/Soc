package com.dikzz.soc.dto.vk;


public class FacebookAccessTokenDto {
	
	private String accessToken;
	private String expiresIn;

	public FacebookAccessTokenDto() {

	}

	public FacebookAccessTokenDto(String accessToken, String expiresIn) {
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
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
}