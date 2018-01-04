package com.citi.alan.myproject.tess4j.enu;

public enum ActivityType {
	GENERAL_ASSISTS("general_assists", "1"), NO_ASSISTS("no_assists", "2"),
	LUCKY_GUY("lucky_guy", "3"), SUPER_ASSISTS("super_assists", "4"), JIAOTONG("jiaotong", "5");
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
