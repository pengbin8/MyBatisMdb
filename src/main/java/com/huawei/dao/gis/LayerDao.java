package com.huawei.dao.gis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.huawei.model.Layer;
import com.huawei.model.UpdateLayer;

/**
 * @author apple
 * @date 2018年2月19日-下午3:27:21
 * @description 图层数据库操作层
 * @version v1.0.0
 * @copyRight .huawei.com
 * @eSpace pwx391198
 */
@Mapper
public interface LayerDao {
	//批量插入，批量刪除，查詢，更新
	boolean inseryLayers(ArrayList<Layer> layers);
	boolean deleteLayers(ArrayList<Integer> ids);
	List<Layer> getLayers(Map<String,Object> map);
	boolean updateLayer(UpdateLayer layer);
}
