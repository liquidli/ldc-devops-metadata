package com.abrainyun.ldc.devops.metadata.service.impl;

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
}
