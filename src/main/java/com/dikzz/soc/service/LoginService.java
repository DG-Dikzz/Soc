package com.dikzz.soc.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Service;

@Service
@Path("/login")
public class LoginService {
	@GET
	public String login() {
		return "login";
	}
}
