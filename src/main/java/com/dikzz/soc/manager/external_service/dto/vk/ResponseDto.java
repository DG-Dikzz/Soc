package com.dikzz.soc.manager.external_service.dto.vk;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class ResponseDto {

	private Integer count;
	
	@JsonProperty(value = "response")
	private JsonNode response;

	//TODO implement error field {"error":{"error_code":3,"error_msg":"Unknown method passed","request_params":[{"key":"oauth","value":"1"},{"key":"method","value":"acc1ount.getProfileInfo"},{"key":"access_token","value":"e4fa6294de859a3b6762ed76ef3f90fcca77d73355a8c220503f9b8cf5cca31b3d447844bc84b9de59702"}]}}
	
	public ResponseDto() {
	}

	public JsonNode getResponce() {
		return response;
	}

	public void setResponce(JsonNode response) {
		this.response = response;
	}
	
	public boolean hasError() {
		return response == null;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
