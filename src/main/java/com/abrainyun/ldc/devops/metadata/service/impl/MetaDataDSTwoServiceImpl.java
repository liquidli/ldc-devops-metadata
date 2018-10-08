package com.abrainyun.ldc.devops.metadata.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abrainyun.ldc.devops.metadata.config.JsonUtil;
import com.abrainyun.ldc.devops.metadata.dao.MetaDataDao;
import com.abrainyun.ldc.devops.metadata.dao.mapper.MetaDataDSTwoMapper;
import com.abrainyun.ldc.devops.metadata.entity.MetaData;
import com.abrainyun.ldc.devops.metadata.service.MetaDataService;

@Service("MetaDataDSTwoService")
@Transactional(transactionManager = "dsTwoTransactionManager", readOnly = true)
public class MetaDataDSTwoServiceImpl implements MetaDataService {

	@Autowired
	private MetaDataDao metaDataDao;

	@Autowired
	private MetaDataDSTwoMapper metaDataDSTwoMapper;

	@Override
	public int selectCount() {
		metaDataDao.setMetaDataBaseMapper(metaDataDSTwoMapper);
		return metaDataDao.selectCount();
	}

	@Override
	public List<MetaData> getAllMetaEntity() {
		List<MetaData> metaDataList = metaDataDao.getAllMetaEntity();
		if (metaDataList != null && metaDataList.size() > 0) {
			for (MetaData metaData : metaDataList) {
				if (StringUtils.isNotEmpty(metaData.getJson())
						&& (metaData.getMappedJson() == null || metaData.getMappedJson().isEmpty())) {
					String json = metaData.getJson();
					metaData.setMappedJson(JsonUtil.parse(json, Map.class));
				}
			}
		}
		return metaDataList;
	}
	
	
	@Override
	public LinkedHashMap<String ,LinkedHashMap<String,String>> getMetaEntities() {
		metaDataDao.setMetaDataBaseMapper(metaDataDSTwoMapper);
		List<MetaData> metaDataList = metaDataDao.getAllMetaEntity();
		List<MetaData> metaFieldDataList = metaDataDao.getAllMetaField();
		LinkedHashMap<String ,LinkedHashMap<String,String>> resMap=new LinkedHashMap<String ,LinkedHashMap<String,String>>();
		String entityName="",id="",parentid="";
		if (metaDataList != null && metaDataList.size() > 0) {
			for (MetaData metaData : metaDataList) {
				if (StringUtils.isNotEmpty(metaData.getJson())
						&& (metaData.getMappedJson() == null || metaData.getMappedJson().isEmpty())) {
					String json = metaData.getJson();
					metaData.setMappedJson(JsonUtil.parse(json, Map.class));
					entityName=String.valueOf(metaData.getMappedJson().get("name"));
					LinkedHashMap<String,String> tempMap=new LinkedHashMap<String ,String>();
					id=String.valueOf(metaData.getMappedJson().get("id"));
					tempMap.put("table_id",id);
					tempMap.put("table_label",String.valueOf(metaData.getMappedJson().get("label")));
					for (MetaData metaFieldData : metaFieldDataList) {
						if (StringUtils.isNotEmpty(metaFieldData.getJson())) {
							if(metaFieldData.getMappedJson() == null || metaFieldData.getMappedJson().isEmpty()) {
								String metaFieldDataJson = metaFieldData.getJson();
								metaFieldData.setMappedJson(JsonUtil.parse(metaFieldDataJson, Map.class));
							}
							parentid=String.valueOf(metaFieldData.getMappedJson().get("parentid"));
							if(id.equals(parentid)) {
								tempMap.put(String.valueOf(metaFieldData.getMappedJson().get("name"))+"~field_name",String.valueOf(metaFieldData.getMappedJson().get("name")));
								tempMap.put(String.valueOf(metaFieldData.getMappedJson().get("name"))+"~field_type",String.valueOf(metaFieldData.getMappedJson().get("fieldType")) );
								tempMap.put(String.valueOf(metaFieldData.getMappedJson().get("name"))+"~field_label",String.valueOf(metaFieldData.getMappedJson().get("label")) );
								tempMap.put(String.valueOf(metaFieldData.getMappedJson().get("name"))+"~field_updated",String.valueOf(metaFieldData.getUpdated().getTime()) );
							}
						}
					}
					
					resMap.put(entityName, tempMap);
				
				}
			}
		}
		return resMap;
	}
}
