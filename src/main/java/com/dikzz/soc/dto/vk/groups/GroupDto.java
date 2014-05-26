package com.dikzz.soc.dto.vk.groups;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class GroupDto {
	private Integer id;
	private String name;
	private String screen_name;
	private boolean is_closed;
	private String type;
	private boolean is_admin;
	private boolean is_member;
	@JsonProperty(value = "photo_50")
	private String pathPhoto50;
	@JsonProperty(value = "photo_100")
	private String pathPhoto100;
	@JsonProperty(value = "photo200")
	private String pathPhoto200;

	public GroupDto() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public boolean isIs_closed() {
		return is_closed;
	}

	public void setIs_closed(boolean is_closed) {
		this.is_closed = is_closed;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public boolean isIs_member() {
		return is_member;
	}

	public void setIs_member(boolean is_member) {
		this.is_member = is_member;
	}

	public String getPathPhoto50() {
		return pathPhoto50;
	}

	public void setPathPhoto50(String pathPhoto50) {
		this.pathPhoto50 = pathPhoto50;
	}

	public String getPathPhoto100() {
		return pathPhoto100;
	}

	public void setPathPhoto100(String pathPhoto100) {
		this.pathPhoto100 = pathPhoto100;
	}

	public String getPathPhoto200() {
		return pathPhoto200;
	}

	public void setPathPhoto200(String pathPhoto200) {
		this.pathPhoto200 = pathPhoto200;
	}
}
