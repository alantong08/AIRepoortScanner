package com.citi.alan.myproject.tess4j.dict;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

	public static  Map<String, String> activityMap =new HashMap<String, String>();
	public static  Map<String, String> tranferMap =new HashMap<String, String>(); 
	static {
		activityMap.put("1","月底助攻");
		activityMap.put("2","月底不助攻");
		activityMap.put("3","幸运星");
		activityMap.put("4","新人福利");
		activityMap.put("5","邀请福利");
		
	    activityMap.put("月底助攻","1");
	    activityMap.put("月底不助攻","2");
	    activityMap.put("幸运星","3");
	    activityMap.put("新人福利","4");
	    activityMap.put("邀请福利","5");

		
		tranferMap.put("1", "阿联");
		tranferMap.put("2", "微信");
		tranferMap.put("3", "支付宝");
		
	    tranferMap.put("阿联", "1");
	    tranferMap.put("微信", "2");
	    tranferMap.put("支付宝", "3");
	}
}
