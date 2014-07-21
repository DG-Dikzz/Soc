package com.dikzz.soc.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.CommunityManagerConfiguration;
import com.dikzz.soc.manager.external_service.dto.vk.AccessTokenDto;
import com.dikzz.soc.manager.social.CommunityType;
import com.dikzz.soc.manager.social.VkManager;
import com.google.common.base.Throwables;

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
	
	@POST
	@Path("/applyAccessTokenUrl")
	public Response applyAccessToken(@FormParam("url") String urlString) {
		try {
			String accessToken = null;
			Integer expiresIn = null;
			String userId = null;
			
			URL url = new URL(urlString);
			String[] parameters = url.getRef().split("&");
			for (String parameter : parameters) {
				String[] parameterParts = parameter.split("=");
				if(parameterParts[0].equals(AccessTokenDto.ACCESS_TOKEN_PARAMETER)) {
					accessToken = parameterParts[1];
				} else if(parameterParts[0].equals(AccessTokenDto.EXPIRES_IN_PARAMETER)) {
					expiresIn = Integer.parseInt(parameterParts[1]);
				} else if(parameterParts[0].equals(AccessTokenDto.USER_ID_PARAMETER)) {
					userId = parameterParts[1];
				} else {
					throw new IllegalStateException("Unknown authorization path parameter");
				}
			}
			
			Calendar exparationDate = Calendar.getInstance();
			exparationDate.add(Calendar.SECOND, expiresIn - 120);
			
			AccessTokenDto accessTokenDto = new AccessTokenDto();
			accessTokenDto.setAccessToken(accessToken);
			accessTokenDto.setExparationDate(exparationDate.getTime());
			
			accessTokenDto.setUserId(userId);
			vkManager.setAccessToken(accessTokenDto);
			return Response.status(Response.Status.OK).build();
		} catch (MalformedURLException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (IllegalStateException e) {
			Throwables.propagate(e);
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
}
