package com.abrainyun.ldc.devops.metadata.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abrainyun.ldc.devops.metadata.entity.MetaData;
import com.abrainyun.ldc.devops.metadata.service.MetaDataService;

@RestController
@RequestMapping("")
public class IndexController {

	@Autowired
	@Qualifier("MetaDataDSOneService")
	private MetaDataService metaDataOneService;

	@Autowired
	@Qualifier("MetaDataDSTwoService")
	private MetaDataService metaDataTwoService;

	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> index() {
		int count01 = metaDataOneService.selectCount();
		List<MetaData> data01 =  metaDataOneService.getAllMetaEntity();

		int count02 = metaDataTwoService.selectCount();
		List<MetaData> data02 =  metaDataTwoService.getAllMetaEntity();

		Map<String, Object> attr01 = new HashMap<>();
		attr01.put("entityCount", count01);
		attr01.put("entityData", data01);

		Map<String, Object> attr02 = new HashMap<>();
		attr02.put("entityCount", count02);
		attr02.put("entityData", data02);

		Map<String, Object> okAttr = new HashMap<>();
		okAttr.put("attr01", attr01);
		okAttr.put("attr02", attr02);

		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String, Object>>(okAttr, HttpStatus.OK);

		return res;
	}
}
