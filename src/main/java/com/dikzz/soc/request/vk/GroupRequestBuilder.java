package com.dikzz.soc.request.vk;

import java.lang.reflect.Field;

import com.dikzz.soc.request.RequestParameter;
import com.dikzz.soc.request.vk.GroupRequestBuilder.GroupRequest.GroupType;
import com.dikzz.soc.request.vk.GroupRequestBuilder.GroupRequest.OrderType;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public final class GroupRequestBuilder {
	public static final class GroupRequest {

		public enum GroupType {
			GROUP("group"), PAGE("page"), EVENT("event");

			private String title;

			private GroupType(String title) {
				this.title = title;
			}

			public String getTitle() {
				return title;
			}

			@Override
			public String toString() {
				return String.valueOf(this.title);
			}
		}

		public enum OrderType {
			BY_USER_COUNT(0), BY_INCREASE_SPEED(1), BY_DAILY_VISITING(2), BY_RELATION_LIKES_TO_USERS(
					3), BY_RELATION_COMMENTS_TO_USERS(4), BY_RELATION_DISCUSSIONS_RECORDS_TO_USERS(
					5);

			private Integer value;

			private OrderType(Integer value) {
				this.value = value;
			}

			public Integer getValue() {
				return value;
			}

			@Override
			public String toString() {
				return String.valueOf(this.value);
			}
		}

		public final static String GROUP_SEARCH = "groups.search";

		@RequestParameter(title = "q")
		String query;
		@RequestParameter(title = "type")
		GroupType type;
		@RequestParameter(title = "country_id")
		Integer countryId;
		@RequestParameter(title = "city_id")
		Integer cityId;
		/**
		 * Only if @see GroupRequest.type has value @see GroupType.EVENT
		 */
		@RequestParameter(title = "future")
		Boolean future;
		@RequestParameter(title = "sort")
		OrderType sort;
		@RequestParameter(title = "offset")
		Integer offset;
		@RequestParameter(title = "count")
		Integer count;
		
		@RequestParameter(title = "v")
		static final String apiVersion = "5.21";

		public GroupRequest(String query) {
			this.query = query;
		}

		public String getQuery() {
			return query;
		}

		public GroupType getType() {
			return type;
		}

		public Integer getCountryId() {
			return countryId;
		}

		public Integer getCityId() {
			return cityId;
		}

		public Boolean isFuture() {
			return future;
		}

		public OrderType getSort() {
			return sort;
		}

		public Integer getOffset() {
			return offset;
		}

		public Integer getCount() {
			return count;
		}

		public static String getApiversion() {
			return apiVersion;
		}
	}

	private final GroupRequest request;

	public GroupRequestBuilder(String query) {
		request = new GroupRequest(query);
	}

	public GroupRequestBuilder setType(GroupType type) {
		request.type = type;
		return this;
	}

	public GroupRequestBuilder setCountryId(Integer countryId) {
		request.countryId = countryId;
		return this;
	}

	public GroupRequestBuilder setCityId(Integer cityId) {
		request.cityId = cityId;
		return this;
	}

	public GroupRequestBuilder setFuture(Boolean future) {
		Preconditions
				.checkState(request.type == GroupType.EVENT,
						"The future parameter can be set only for type == GroupType.EVENT");
		request.future = future;
		return this;
	}

	public GroupRequestBuilder setSort(OrderType sort) {
		request.sort = sort;
		return this;
	}

	public GroupRequestBuilder setOffset(Integer offset) {
		request.offset = offset;
		return this;
	}

	public GroupRequestBuilder setCount(Integer count) {
		// TODO make via logger
		if (count != null && count > 1000) {
			System.out.println("WARN: Cannot be greater then 1000");
		}
		request.count = count;
		return this;
	}

	public String build() {
		Class<GroupRequest> clazz = GroupRequest.class;
		StringBuilder result = new StringBuilder();
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(RequestParameter.class)) {
					String title = fields[i].getAnnotation(
							RequestParameter.class).title();
					Object value = fields[i].get(request);
					if (value != null) {
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
		}
		return result.toString();
	}

	public GroupRequest getGroupRequest() {
		return request;
	}
}
