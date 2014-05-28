package com.dikzz.soc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dikzz.soc.dto.vk.ResponseDto;
import com.dikzz.soc.request.vk.GroupRequestBuilder;
import com.dikzz.soc.request.vk.GroupRequestBuilder.GroupRequest.GroupType;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public final class RestUtils {
	
	public interface ResponseHandler<T> {
		public T handle(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException;
	}
	
	private RestUtils() {
		
	}
	
	public static <T> T request(String url, ResponseHandler<T> responseHandler) { 
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);

			CloseableHttpResponse response = httpClient.execute(httpget);
			
			return responseHandler.handle(response.getEntity()
					.getContent());
		} catch (ClientProtocolException e) {
			Throwables.propagate(e);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		return null;
	}
	
	
	public static ResponseDto convertToVKResponse(InputStream inputStream, ObjectMapper objectMapper) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(inputStream, ResponseDto.class);
	}
}
