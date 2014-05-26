package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Service;

import com.dikzz.soc.manager.social.FacebookManager;

@Service
@Path("/facebook")
public class FacebookService {

	@Autowired
	private FacebookManager facebookManager;
	
	@GET
	public Response facebookAuthorization(@QueryParam("code") String code) {
		if (code != null) {
			facebookManager.setAccessCode(code);
			return Response.ok(facebookManager.getUserProfile().getFirstName())
					.build();
		} else {
			try {
				return Response.temporaryRedirect(
						new URI(facebookManager.getAuthorizationUrl())).build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@GET
	@Path("/status/{uu}")
	public Object updateStatus(@PathParam("uu") String uu) {
		facebookManager.updateStatus(uu);
		return uu;
	}

	@GET
	@Path("/fullName")
	public Object fullName() {
		return facebookManager.getFullName();
	}
	
	@GET
	@Path("/test")
	public String test() {
		FacebookProfile facebookProfile = facebookManager.test();
		return facebookProfile.getFirstName() + " "
				+ facebookProfile.getLastName();
	}
}
