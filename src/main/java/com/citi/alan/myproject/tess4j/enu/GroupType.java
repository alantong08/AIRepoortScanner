package com.citi.alan.myproject.tess4j.enu;

public enum GroupType {
	NEWER_GROUP("入门群"), PRIMARY_GROUP("初级群"),
	MIDDLE_GROUP("中级群"), SENIOR_GROUP("高级群"), ACCOUNTING_GROUP("会计群");
    
	private String name;
	private GroupType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
