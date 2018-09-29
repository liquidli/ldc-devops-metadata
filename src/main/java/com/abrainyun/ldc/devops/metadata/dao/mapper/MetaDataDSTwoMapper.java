package com.abrainyun.ldc.devops.metadata.dao.mapper;

import com.abrainyun.ldc.devops.metadata.annotations.UseDataSourceTwo;
import com.abrainyun.ldc.devops.metadata.entity.MetaData;

@UseDataSourceTwo
public interface MetaDataDSTwoMapper extends MetaDataBaseMapper<MetaData> {

}
