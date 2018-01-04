package com.citi.alan.myproject.tess4j.enu;

public enum TransferType {
	ELIANPAY("elian", "1"), WEIXINPAY("wexin", "2"), ALIPAY("alipay", "3");
	private String name;
	private String value; 
	private TransferType(String name, String value) {
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
