package com.huawei.enums;

/**
 * @author apple
 * @date 2018年2月19日-下午3:17:42
 * @description 枚举类
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
public enum Enums {
	DEV("DEV",1),
	DEV2("DEV2",2),
	DEV3("DEV3",3);
	
	private String key;
	private int value;
	
	Enums(String key,int value) {
		this.key=key;
		this.value=value;
	}

	public String getKey() {
		return key;
	}
}
