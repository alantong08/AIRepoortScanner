package com.citi.alan.myproject.tess4j.dict;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

	public static  Map<String, String> activityMap =new HashMap<String, String>();
	public static  Map<String, String> tranferMap =new HashMap<String, String>(); 
	static {
		activityMap.put("1","月底助攻");
		activityMap.put("2","月底不助攻");
		activityMap.put("3","幸运星待遇");
		activityMap.put("4","高端群特供撸卡");
		activityMap.put("5","小娇周周特别活动0.1");
	    activityMap.put("月底助攻","1");
	    activityMap.put("月底不助攻","2");
		
		tranferMap.put("1", "阿联");
		tranferMap.put("2", "微信");
		tranferMap.put("3", "支付宝");
	    tranferMap.put("阿联", "1");
	    tranferMap.put("微信", "2");
	    tranferMap.put("支付宝", "3");
	}
}
