package com.abrainyun.ldc.devops.metadata.dao;

import org.springframework.stereotype.Component;

import com.abrainyun.ldc.devops.metadata.dao.mapper.MetaDataBaseMapper;

@Component
public class MetaDataDao {

	private MetaDataBaseMapper metaDataBaseMapper;

	public int selectCount() {
		return metaDataBaseMapper.selectCount(null);
	}

	public void setMetaDataBaseMapper(MetaDataBaseMapper metaDataBaseMapper) {
		this.metaDataBaseMapper = metaDataBaseMapper;
	}

}
