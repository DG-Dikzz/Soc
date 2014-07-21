package com.dikzz.soc.manager.external_service.dto.vk;

import java.util.Date;

public class AccessTokenDto {
	
	public final static String ACCESS_TOKEN_PARAMETER = "access_token";
	public final static String EXPIRES_IN_PARAMETER = "expires_in";
	public final static String USER_ID_PARAMETER = "user_id";
	
	private String accessToken;
	private Date exparationDate;
	private String userId;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getExparationDate() {
		return exparationDate;
	}

	public void setExparationDate(Date createDate) {
		this.exparationDate = createDate;
	}
}