package com.abrainyun.ldc.devops.metadata.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.abrainyun.ldc.devops.metadata.dao.mapper.MetaDataBaseMapper;
import com.abrainyun.ldc.devops.metadata.entity.MetaData;
import com.github.pagehelper.PageHelper;

@Component
public class MetaDataDao {

	private MetaDataBaseMapper metaDataBaseMapper;

	
	public List<MetaData> getAllMetaEntity() {
		MetaData condition = new MetaData();
		condition.setTypeName("metaEntity");
		PageHelper.orderBy("TENANT_ID ASC,json->'name' ASC");
		return metaDataBaseMapper.select(condition);
	}
	
	public List<MetaData> getAllMetaField() {
		MetaData condition = new MetaData();
		condition.setTypeName("metaField");
		PageHelper.orderBy("TENANT_ID ASC,json->'name' ASC");
		return metaDataBaseMapper.select(condition);
	}
	public int selectCount() {
		return metaDataBaseMapper.selectCount(null);
	}

	public void setMetaDataBaseMapper(MetaDataBaseMapper metaDataBaseMapper) {
		this.metaDataBaseMapper = metaDataBaseMapper;
	}

}
