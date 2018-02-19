package com.huawei.dao.platformat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.huawei.model.Layer;
import com.huawei.model.LayerPlatformat;
import com.huawei.model.UpdateLayerPlatformat;

/**
 * @author apple
 * @date 2018年2月19日-下午3:38:36
 * @description 
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@Mapper
public interface LayerPlatformatDao {
	//批量插入，批量刪除，查詢，更新
	boolean inseryLayers(ArrayList<LayerPlatformat> layers);
	boolean deleteLayers(ArrayList<Integer> ids);
	List<Layer> getLayers(Map<String,Object> map);
	boolean updateLayer(UpdateLayerPlatformat layer);
}
