package com.abrainyun.ldc.devops.metadata.dao.mapper;

import com.abrainyun.ldc.devops.metadata.annotations.UseDataSourceOne;
import com.abrainyun.ldc.devops.metadata.entity.MetaData;

@UseDataSourceOne
public interface MetaDataDSOneMapper extends MetaDataBaseMapper<MetaData> {

}
