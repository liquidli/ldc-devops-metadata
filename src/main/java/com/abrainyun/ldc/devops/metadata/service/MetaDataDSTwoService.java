package com.abrainyun.ldc.devops.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abrainyun.ldc.devops.metadata.dao.MetaDataDao;
import com.abrainyun.ldc.devops.metadata.dao.mapper.MetaDataDSTwoMapper;

@Service
@Transactional(transactionManager = "dsTwoTransactionManager", readOnly = true)
public class MetaDataDSTwoService {

	@Autowired
	private MetaDataDao metaDataDao;

	@Autowired
	private MetaDataDSTwoMapper metaDataDSTwoMapper;

	public int selectCount() {
		metaDataDao.setMetaDataBaseMapper(metaDataDSTwoMapper);
		return metaDataDao.selectCount();
	}

}
