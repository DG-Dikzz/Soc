package com.dikzz.soc.manager.external_service.dto.vk.groups;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class GroupsSearchDto {
	private Integer count;
	private List<GroupDto> items;
	
	public GroupsSearchDto() {
		
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<GroupDto> getItems() {
		return items;
	}

	public void setItems(List<GroupDto> items) {
		this.items = items;
	}
}
