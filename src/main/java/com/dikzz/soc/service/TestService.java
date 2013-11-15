package com.dikzz.soc.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.test.TestClass;

@Service
@Path("/test")
public class TestService {
	
	@Autowired
	private TestClass testClass;
	
	@GET
	@Path("/{mm}")
	public Response getValue(@PathParam("mm") String mm) {
		return  Response.status(200).entity(testClass.buildString(mm)).build();
	}
}
