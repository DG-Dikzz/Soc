package com.dikzz.soc.request.facebook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import com.dikzz.soc.request.Request;
import com.dikzz.soc.request.RequestParameter;
import com.google.common.base.Throwables;

public class FacebookAccessTokenRequest implements Request {
	
	private static final String GET_METHOD = "get";

	public static enum ResponseType {
		CODE("code");

		private String value;

		private ResponseType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	public static enum ScopeValue {
		USER_STATUS("user_status"), FRIENDS_STATUS("friends_status"), EXPORT_STREAM(
				"export_stream"), PUBLISH_ACTIONS("publish_actions"), STATUS_UPDATE(
				"status_update");

		private String value;

		private ScopeValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}
	
	
	public static class FacebookAccessTokenRequestBuilder {
		
		private final FacebookAccessTokenRequest request;
		
		public FacebookAccessTokenRequestBuilder(String clientId, String redirectUri) {
			request = new FacebookAccessTokenRequest(clientId, redirectUri);
		}

		public FacebookAccessTokenRequestBuilder setResponseType(ResponseType responseType) {
			request.setResponseType(responseType);
			return this;
		}

		public FacebookAccessTokenRequestBuilder setScope(ScopeValue... scope) {
			EnumSet<ScopeValue> scopes = EnumSet.noneOf(ScopeValue.class);
			scopes.addAll(Arrays.asList(scope));
			request.setScope(scopes);
			return this;
		}

		public FacebookAccessTokenRequest getRequest() {
			return request;
		}

		public FacebookAccessTokenRequestBuilder setClientSecret(String clientSecret) {
			request.setClientSecret(clientSecret);
			return this;
		}

		public FacebookAccessTokenRequestBuilder setCode(String code) {
			request.setCode(code);
			return this;
		}
	}

	// https://graph.facebook.com/oauth/authorize?client_id=808695732477712&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2FSoc%2Fapi%2Ffacebook&scope=user_status%2C+friends_status%2C+export_stream%2C+publish_actions%2C+status_update
	@RequestParameter(title = "client_id")
	private String clientId;
	@RequestParameter(title = "response_type")
	private ResponseType responseType;
	@RequestParameter(title = "scope")
	private EnumSet<ScopeValue> scope;
	@RequestParameter(title = "redirect_uri")
	private String redirectUri;
	@RequestParameter(title = "client_secret")
	private String clientSecret;
	@RequestParameter(title = "code")
	private String code;

	public FacebookAccessTokenRequest(String clientId, String redirectUri) {
		this.clientId = clientId;
		this.redirectUri = redirectUri;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public EnumSet<ScopeValue> getScope() {
		return scope;
	}

	public void setScope(EnumSet<ScopeValue> scope) {
		this.scope = scope;
	}
	
	public static FacebookAccessTokenRequestBuilder build(String clientId, String redirectUri) {
		return new FacebookAccessTokenRequestBuilder(clientId, redirectUri);
	}
	
	//TODO implement util method
	@Override
	public String getParameters() {

		Class<FacebookAccessTokenRequest> clazz = FacebookAccessTokenRequest.class;
		StringBuilder result = new StringBuilder();
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(RequestParameter.class)) {
					String title = fields[i].getAnnotation(
							RequestParameter.class).title();

					StringBuilder methodName = new StringBuilder(
							fields[i].getName());
					methodName.setCharAt(0,
							Character.toUpperCase(methodName.charAt(0)));
					methodName.insert(0, GET_METHOD);
					Object value = clazz.getMethod(methodName.toString())
							.invoke(this);

					if (value != null) {

						if (value instanceof Collection<?>) {
							String temp = value.toString();
							value = temp.substring(1, temp.length() - 1).replaceAll(" ", "%20");
						}

						if (result.length() > 0) {
							result.append("&");
						}
						result.append(title).append("=").append(value);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			Throwables.propagate(e);
		} catch (IllegalAccessException e) {
			Throwables.propagate(e);
		} catch (InvocationTargetException e) {
			Throwables.propagate(e);
		} catch (NoSuchMethodException e) {
			Throwables.propagate(e);
		} catch (SecurityException e) {
			Throwables.propagate(e);
		}
		return result.toString();
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
