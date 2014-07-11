package com.dikzz.soc.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Path("/login")
public class LoginService {

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static final class LoginResponse {
		private boolean success;

		public LoginResponse(boolean success) {
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(readOnly = true)
	public LoginResponse login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null && !session.isNew()) {
			session.invalidate();
		}
		request.getSession(true);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		boolean success;
		try {
			SecurityContextHolder.getContext().setAuthentication(
					authenticationManager.authenticate(token));
			success = true;
		} catch (AuthenticationException e) {
			success = false;
		}

		return new LoginResponse(success);
	}
}
