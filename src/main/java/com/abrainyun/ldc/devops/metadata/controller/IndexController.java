package com.abrainyun.ldc.devops.metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abrainyun.ldc.devops.metadata.service.MetaDataDSOneService;
import com.abrainyun.ldc.devops.metadata.service.MetaDataDSTwoService;

@RestController
@RequestMapping("")
public class IndexController {

	@Autowired
	private MetaDataDSOneService metaDataOneService;

	@Autowired
	private MetaDataDSTwoService metaDataTwoService;

	@GetMapping("/")
	public String index() {
		int count01 = metaDataOneService.selectCount();
		int count02 = metaDataTwoService.selectCount();

		return String.valueOf("Count01:" + count01 + " Count02:" + count02);
	}
}
