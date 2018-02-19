package com.huawei.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.model.Layer;
import com.huawei.model.UpdateLayer;
import com.huawei.service.LayerPlatformatService;
import com.huawei.service.LayerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author apple
 * @date 2018年2月19日-下午3:23:41
 * @description 图层控制器
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@RestController
@Api(description="图层控制器")
public class LayerController {

	private Logger logger = Logger.getLogger("LayerController");
	
	@Autowired
	private LayerPlatformatService layerPlatformatService;
	
	@Autowired
	private LayerService layerService;
	
	@ApiOperation(value = "新建图层")
	@RequestMapping(value="/gis/insertLayers",method=RequestMethod.POST)
	public boolean insertLayers(@RequestBody(required=true)
		ArrayList<Layer> layers) throws Exception {
		try {
			layerService.inseryLayers(layers);
			return true;
		} catch (Exception e) {
			e.getStackTrace();
			throw e;
		}
	}
	
	@RequestMapping(value="/gis/deleteLayers",method=RequestMethod.GET)
	@ApiOperation(value = "批量删除图层")
	@Transactional(rollbackFor=RuntimeException.class)
	public boolean deleteLayers(@RequestParam(required=true) ArrayList<Integer> ids) {
		layerService.deleteLayers(ids);
		int i=1/0;
		System.out.println("====="+i);
		layerPlatformatService.deleteLayers(ids);
		return true;
	}
	
	@RequestMapping(value="/gis/getLayers",method=RequestMethod.GET)
	@ApiOperation(value = "图层查询")
	public List<Layer> getLayers(String appId,String objectCode) {
		logger.info("appid:"+appId+"objectcode:"+objectCode);
		return layerService.getLayers(appId,objectCode);
	}
	
	@RequestMapping(value="/gis/updateLayer",method=RequestMethod.POST)
	@ApiOperation(value = "图层更新")
	public boolean updateLayer(@RequestBody(required=true) UpdateLayer layer) {
		return layerService.updateLayer(layer);
	}

	
}
