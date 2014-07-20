package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.CommunityManagerConfiguration;
import com.dikzz.soc.manager.social.CommunityType;
import com.dikzz.soc.manager.social.VkManager;

@Service
@Path("/vk")
public class VkService {

	
	@Autowired
	private CommunityManagerConfiguration communityManagerConfiguration;
	
	private VkManager vkManager;
	
	@PostConstruct
	private void initTwitterManager() {
		vkManager = (VkManager) communityManagerConfiguration.getSocialManager(CommunityType.VK);
	}


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
	public Response applyAccessToken(@QueryParam("code") String code) {
		vkManager.setAccessCode(code);
		return Response.ok(Response.Status.OK).build();
	}
}
