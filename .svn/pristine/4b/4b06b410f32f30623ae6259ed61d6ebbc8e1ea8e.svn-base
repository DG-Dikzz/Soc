package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.dto.vk.account.UserProfileDto;
import com.dikzz.soc.manager.social.VkManager;

@Service
@Path("/vk")
public class VkService {

	@Autowired
	private VkManager vkManager;
	
	@GET
	public Response vkAuthorization() {
		try {
			return Response.temporaryRedirect(
					new URI(vkManager.getAuthorizationUrl())).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GET
	@Path("/applyAccessToken")
	public void applyAccessToken(@QueryParam("code") String code) {
		vkManager.setAccessCode1(code);
	}
	
	@GET
	@Path("/userProfile")
	public UserProfileDto getUserProfile() {
		UserProfileDto userProfileDto = vkManager.getUserProfile();
		return userProfileDto;
	}
}
