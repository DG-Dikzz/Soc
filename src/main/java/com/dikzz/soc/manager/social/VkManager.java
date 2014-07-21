package com.dikzz.soc.manager.social;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dikzz.soc.dto.api.SocialAccessStatus;
import com.dikzz.soc.dto.api.SocialAccessStatus.SocialAccessState;
import com.dikzz.soc.dto.api.SocialCommunity;
import com.dikzz.soc.manager.external_service.dto.vk.AccessTokenDto;
import com.dikzz.soc.manager.external_service.dto.vk.ResponseDto;
import com.dikzz.soc.manager.external_service.dto.vk.groups.GroupDto;
import com.dikzz.soc.manager.external_service.dto.vk.groups.GroupsSearchDto;
import com.dikzz.soc.request.vk.GroupRequestBuilder;
import com.dikzz.soc.request.vk.GroupRequestBuilder.GroupRequest;
import com.dikzz.soc.utils.RestUtils;
import com.dikzz.soc.utils.RestUtils.ResponseHandler;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

@Repository
public class VkManager implements SocialManager {

	@Value("${vk.app.id}")
	private String vkAppId;

	private AccessTokenDto accessTokenDto;

	private final static String CALLBACK_URL = "https://oauth.vk.com/blank.html";
	private final static String VK_SCOPE_ALL = "9999999";
	private final static String VK_AUTHORIZATION_DISPLAY = "popup";
	private final static String VK_API_VERSION = "5.14";
	private final static String AUTH_URL = "https://oauth.vk.com/authorize?client_id={0}&scope={1}&redirect_uri={2}&display={3}&v={4}&response_type=token";
	private final static String VK_METHOD_REQUEST_PATTERN = "https://api.vk.com/method/{0}?{1}{2}access_token={3}";

	public String getAuthorizationUrl() {
		return MessageFormat.format(AUTH_URL, vkAppId, VK_SCOPE_ALL,
				CALLBACK_URL, VK_AUTHORIZATION_DISPLAY, VK_API_VERSION);
	}

	public void setAccessToken(AccessTokenDto accessTokenDto) {
		this.accessTokenDto = accessTokenDto;
	}

	private List<GroupDto> getGroups(String request) {

		String requestUrl = getVkMethodRequest(GroupRequest.GROUP_SEARCH,
				request);
		return RestUtils.request(requestUrl,
				new ResponseHandler<List<GroupDto>>() {

					@Override
					public List<GroupDto> handle(ResponseDto responseDto)
							throws JsonParseException, JsonMappingException,
							IOException {
						GroupsSearchDto groupsSearchDto = null;
						if (!responseDto.hasError()) {
							ObjectMapper objectMapper = new ObjectMapper();
							groupsSearchDto = objectMapper.readValue(
									responseDto.getResponce(),
									GroupsSearchDto.class);

						}
						return groupsSearchDto.getItems();
					}

				});

	}

	private String getVkMethodRequest(String method, String parameters) {
		Preconditions.checkNotNull(accessTokenDto, "You are not authorized.");
		return MessageFormat.format(VK_METHOD_REQUEST_PATTERN, method,
				!Strings.isNullOrEmpty(parameters) ? parameters : "",
				!Strings.isNullOrEmpty(parameters) ? "&" : "",
				accessTokenDto.getAccessToken());
	}

	@Override
	public List<SocialCommunity<?>> getCommunities(String query,
			Integer page) {
		List<GroupDto> groupDtos = getGroups(new GroupRequestBuilder(
				Strings.nullToEmpty(query)).setOffset(PAGE_COUNT * page).setCount(PAGE_COUNT)
				.build());

		return Lists.transform(groupDtos,
				new Function<GroupDto, SocialCommunity<?>>() {

					@Override
					public SocialCommunity<?> apply(GroupDto object) {
						return CommunityType.VK
								.getSocialCommunityInstance(object);

					}
				});
	}
	
	@Override
	public SocialAccessStatus getAccessStatus() {
		SocialAccessState state = null;
		if (accessTokenDto != null && Calendar.getInstance().getTime().before(accessTokenDto.getExparationDate())) {
			state = SocialAccessState.ACCESSIBLE;
		} else {
			state = SocialAccessState.INACCESSIBLE;
		}
		return new SocialAccessStatus(state, CommunityType.VK);
	}
}
