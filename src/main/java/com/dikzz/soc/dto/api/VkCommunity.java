package com.dikzz.soc.dto.api;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.dikzz.soc.manager.external_service.dto.vk.groups.GroupDto;
import com.dikzz.soc.manager.social.CommunityType;

public class VkCommunity implements SocialCommunity<GroupDto> {

	@JsonIgnore
	private GroupDto groupDto;

	public VkCommunity() {

	}

	@Override
	public String getTitle() {
		return groupDto.getScreen_name();
	}

	@Override
	public String getDescription() {
		return groupDto.getName();
	}

	public GroupDto getGroupDto() {
		return groupDto;
	}

	public void setGroupDto(GroupDto groupDto) {
		this.groupDto = groupDto;
	}

	@Override
	public CommunityType getCommunityType() {
		return CommunityType.VK;
	}

	@Override
	public void setAdaptableObject(GroupDto object) {
		groupDto = object;
	}

	@Override
	public String getId() {
		return String.valueOf(groupDto.getId());
	}

}
