package com.huawei.model;

/**
 * @author apple
 * @date 2018年2月19日-下午3:27:52
 * @description 图层更新模型
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
public class UpdateLayerPlatformat {
	private Integer id;
	private String appId;
	private String objectCode;
	private String projectCode;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
