package com.dikzz.soc.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Service;

import com.dikzz.soc.dao.TestDao;
import com.dikzz.soc.manager.FacebookManager;
import com.dikzz.soc.manager.TwitterManager;
import com.dikzz.soc.model.TestTable;

@Service
@Path("/test")
public class TestService {

	@Autowired
	private TestDao testDao;
	
	@Autowired
	private FacebookManager facebookManager;
	
	@Autowired
	private TwitterManager twitterManager;

	@GET
	@Path("/get/{id}")
	public Response getValue(@PathParam("id") String id) {
		return Response
				.status(200)
				.entity("This: "
						+ testDao.getById(Integer.parseInt(id)).getName())
				.build();
	}
	
	@GET
	@Path("/add/{id}/{name}")
	public String addValue(@PathParam("id") String id, @PathParam("name") String name) {
		TestTable in = new TestTable();
		in.setId(Integer.parseInt(id));
		in.setName(name);
		testDao.addTestDao(in);
		return "Done";
	}
	
	@GET
	@Path("/all")
	public List<TestTable> getAll() {
		return testDao.getAll();
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
	@Path("/facebookAuthorization")
	public Response facebookAuthorization(@QueryParam("code") String code) {
		if(code != null) {
			facebookManager.setAccessCode(code);
			return Response.ok(facebookManager.getUserProfile().getFirstName()).build();
		} else {
			try {
				return Response.temporaryRedirect(new URI(facebookManager.getAuthorizationUrl())).build();
			} catch (URISyntaxException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@GET
	@Path("/twitterAuthorization")
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
	
	@GET
	@Path("/test")
	public String test() {
		FacebookProfile facebookProfile = facebookManager.test();
		return facebookProfile.getFirstName() + " " + facebookProfile.getLastName();
	}
	
	@GET
	@Path("/message")
	@Produces(MediaType.TEXT_PLAIN)
	public String mesage() {
		return "message";
	}
	
	@GET
	@Path("/dikzz")
	public String testr() {

	    return "Wrong";
	}
}
