package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.manager.social.TwitterManager;

@Service
@Path("/twitter")
public class TwitterService {
	@Autowired
	private TwitterManager twitterManager;

	@GET
	public Response twitterAuthorization(
			@QueryParam("oauth_token") String oAuthToken,
			@QueryParam("oauth_verifier") String oAuthVerifier) {
		if (oAuthToken != null) {
			twitterManager.setAccessCode(oAuthToken, oAuthVerifier);
			return Response.ok(twitterManager.getFullName()).build();
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
