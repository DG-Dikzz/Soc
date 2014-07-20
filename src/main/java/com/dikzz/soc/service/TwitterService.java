package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.CommunityManagerConfiguration;
import com.dikzz.soc.dto.api.SocialAccessStatus;
import com.dikzz.soc.manager.social.CommunityType;
import com.dikzz.soc.manager.social.TwitterManager;

@Service
@Path("/twitter")
public class TwitterService {
	
	@Autowired
	private CommunityManagerConfiguration communityManagerConfiguration;
	
	private TwitterManager twitterManager;
	
	@PostConstruct
	private void initTwitterManager() {
		twitterManager = (TwitterManager) communityManagerConfiguration.getSocialManager(CommunityType.TWITTER);
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response twitterAuthorization(
			@QueryParam("oauth_token") String oAuthToken,
			@QueryParam("oauth_verifier") String oAuthVerifier) {
		if (oAuthToken != null) {
			twitterManager.setAccessCode(oAuthToken, oAuthVerifier);
			return Response.ok(Response.Status.OK).build();
		} else {
			try {
				return Response.temporaryRedirect(
						new URI(twitterManager.getAuthorizationUrl())).build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
