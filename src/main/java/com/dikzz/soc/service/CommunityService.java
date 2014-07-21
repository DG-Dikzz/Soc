package com.dikzz.soc.service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dikzz.soc.CommunityManagerConfiguration;
import com.dikzz.soc.dto.api.SocialAccessStatus;
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
			@QueryParam("communitiesTypes") List<CommunityType> communitiesTypes,
			@QueryParam("page") Integer page) {

		List<SocialCommunity<?>> list = new ArrayList<SocialCommunity<?>>();

		EnumSet<CommunityType> communitiesTypesSet = EnumSet
				.noneOf(CommunityType.class);
		communitiesTypesSet.addAll(communitiesTypes);

		for (CommunityType communityType : communitiesTypesSet) {
			list.addAll(socialManagerConfiguration.getSocialManager(
					communityType).getCommunities(query, page));
		}
		return list;
	}

	@GET
	@Path("accessStatus/{communityType}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public SocialAccessStatus getSocialAccessStatus(
			@PathParam("communityType") CommunityType communityType) {
		return socialManagerConfiguration.getSocialManager(communityType)
				.getAccessStatus();
	}
}
