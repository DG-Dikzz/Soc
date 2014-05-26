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
import com.dikzz.soc.manager.social.FacebookManager;

@Service
@Path("/facebook")
public class FacebookService {

	@Autowired
	private CommunityManagerConfiguration communityManagerConfiguration;
	
	private FacebookManager facebookManager;
	
	@PostConstruct
	private void initTwitterManager() {
		facebookManager = (FacebookManager) communityManagerConfiguration.getSocialManager(CommunityType.FACEBOOK);
	}

	/*@Autowired
	private CommunityManagerConfiguration socialManagerConfiguration;
	
	@PostConstruct
	private void getManager() {
		
	}*/

	@GET
	public Response facebookAuthorization(@QueryParam("code") String code) {
		if (code != null) {
			facebookManager.setAccessCode(code);
			return Response.ok(Response.Status.OK).build();
		} else {
			try {
				return Response.temporaryRedirect(
						new URI(facebookManager.getAuthorizationUrl())).build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/*
	 * @GET
	 * 
	 * @Path("/groups")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<Group>
	 * getGroups(@QueryParam("query") String query, @QueryParam("offset")
	 * Integer offset, @QueryParam("count") Integer count) { return
	 * facebookManager.getGroups(query, offset, count); }
	 */
}
