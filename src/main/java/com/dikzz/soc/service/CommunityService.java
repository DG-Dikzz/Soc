package com.dikzz.soc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.CommunityManagerConfiguration;
import com.dikzz.soc.dto.api.SocialCommunity;
import com.dikzz.soc.manager.social.CommunityType;

@Service
@Path("/community")
public class CommunityService {

	@Autowired
	private CommunityManagerConfiguration socialManagerConfiguration;

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public List<SocialCommunity<?>> getCommunities(
			@QueryParam("query") String query,
			@QueryParam("communitiesTypes") CommunityType[] communitiesTypes,
			@QueryParam("page") Integer page) {

		EnumSet<CommunityType> communitiesTypesSet = EnumSet
				.noneOf(CommunityType.class);
		communitiesTypesSet.addAll(Arrays.asList(communitiesTypes));

		/*
		 * Integer countforEachType = null; Integer offsetForEachType = null; if
		 * (!communitiesTypesSet.isEmpty() && count != null) {
		 * Preconditions.checkArgument(communitiesTypesSet.size() <= count,
		 * "Count can not be less than communityType quantity");
		 * countforEachType = count / communitiesTypesSet.size(); if (offset !=
		 * null) { Preconditions.checkArgument(communitiesTypesSet.size() <
		 * count, "Count can not be less than communityType quantity");
		 * offsetForEachType = offset / communitiesTypesSet.size(); }
		 * 
		 * }
		 */

		List<SocialCommunity<?>> list = new ArrayList<SocialCommunity<?>>();

		for (CommunityType communityType : communitiesTypesSet) {
			list.addAll(socialManagerConfiguration.getSocialManager(
					communityType).getCommunities(query, page));
		}
		return list;
	}
}
