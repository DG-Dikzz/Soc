package com.dikzz.soc.utils;

import java.io.IOException;
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

import com.dikzz.soc.manager.external_service.dto.vk.ResponseDto;
import com.dikzz.soc.request.vk.GroupRequestBuilder;
import com.dikzz.soc.request.vk.GroupRequestBuilder.GroupRequest.GroupType;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public final class RestUtils {
	
	public interface ResponseHandler<T> {
		public T handle(ResponseDto responseDto) throws JsonParseException, JsonMappingException, IOException;
	}
	
	private RestUtils() {
		
	}
	
	public static <T> T request(String url, ResponseHandler<T> responseHandler) { 
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);

			CloseableHttpResponse response = httpClient.execute(httpget);

			ObjectMapper objectMapper = new ObjectMapper();

			ResponseDto result = objectMapper.readValue(response.getEntity()
					.getContent(), ResponseDto.class);
			
			return responseHandler.handle(result);
		} catch (ClientProtocolException e) {
			Throwables.propagate(e);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<Integer>();
		ints.add(1);
		ints.add(2);
		
		class Mut {
			Map<Integer, Integer> sets = new HashMap<Integer, Integer>();

			public Map<Integer, Integer> getSets() {
				return sets;
			}

			public void setSets(Map<Integer, Integer> sets) {
				this.sets = sets;
			}
		}
		
		final Mut mut = new Mut();
		
		List<String> strings = Lists.transform(ints, new Function<Integer, String>() {

			@Override
			public String apply(Integer arg0) {
				mut.getSets().put(arg0, arg0);
				return arg0.toString();
			}
		});
		
		System.out.println(mut.getSets() + "\n " + strings);
	}
}
