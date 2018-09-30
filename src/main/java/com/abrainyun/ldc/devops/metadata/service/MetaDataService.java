package com.abrainyun.ldc.devops.metadata.service;

import java.util.List;

import com.abrainyun.ldc.devops.metadata.entity.MetaData;

public interface MetaDataService {

	int selectCount();

	List<MetaData> getAllMetaEntity();

}