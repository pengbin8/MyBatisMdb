package com.huawei.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huawei.dao.gis.LayerDao;
import com.huawei.model.Layer;
import com.huawei.model.UpdateLayer;

/**
 * @author apple
 * @date 2018年2月19日-下午3:26:55
 * @description 图层服务
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@Service
public class LayerService {
	
	@Autowired
	private LayerDao layerDao;
	
	public boolean inseryLayers(ArrayList<Layer> layers) {
		return layerDao.inseryLayers(layers);
	}
	
	public boolean deleteLayers(ArrayList<Integer> ids) {
		return layerDao.deleteLayers(ids);
	}
	
	public List<Layer> getLayers(String appId,String objectCode){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("APPID",appId);
		map.put("OBJECTCODE",objectCode);
		return layerDao.getLayers(map);
	}
	
	public boolean updateLayer(UpdateLayer layer) {
		return layerDao.updateLayer(layer);
	}
}
