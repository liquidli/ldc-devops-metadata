package com.abrainyun.ldc.devops.metadata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abrainyun.ldc.devops.metadata.dao.MetaDataDao;
import com.abrainyun.ldc.devops.metadata.dao.mapper.MetaDataDSOneMapper;

@Service
@Transactional(transactionManager = "dsOneTransactionManager", readOnly = true)
public class MetaDataDSOneService {

	@Autowired
	private MetaDataDao metaDataDao;

	@Autowired
	private MetaDataDSOneMapper metaDataDSOneMapper;

	public int selectCount() {
		metaDataDao.setMetaDataBaseMapper(metaDataDSOneMapper);
		return metaDataDao.selectCount();
	}

}
