package com.citi.alan.myproject.tess4j.enu;

public enum ActivityType {
	GENERAL_ASSISTS("general_assists", "1"), NO_ASSISTS("no_assists", "2"),
	LUCKY_GUY("lucky_guy", "3"), NEW_WELFARE("new_welfare", "4"), INVITE_ACTIVITY("invite_activity", "5"),
	WECHAT_FOLLOWING("wechat_following", "6"), BLOOD_RETURN("blood_return", "7"), WEIXIN_ZERO_RATE("weixin_zero_rate", "8"); 
	private String name;
	private String value; 
	private ActivityType(String name, String value) {
		this.name = name;
		this.value =  value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
