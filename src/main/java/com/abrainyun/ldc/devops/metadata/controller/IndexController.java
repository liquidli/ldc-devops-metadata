package com.abrainyun.ldc.devops.metadata.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
//		okAttr.put("attr01", attr01);
//		okAttr.put("attr02", attr02);
		LinkedHashMap<String ,LinkedHashMap<String,String>> resMap=   metaDataOneService.getMetaEntities();
		okAttr.put("resMap", resMap);
		
		
		
		
		ResponseEntity<Map<String, Object>> res = new ResponseEntity<Map<String, Object>>(okAttr, HttpStatus.OK);

		return res;
	}
	
	/**
	 * 首页访问
	 * @return
	 */
	@GetMapping("/index") 
	public ModelAndView index2(){
		   ModelAndView mv = new ModelAndView("index");   
		    return mv;  
	}
	/**
	 * 数据结构比较
	 * dataSourceCompare
	 * @param isChecked 1 选中快速查询 其他没有选中
	 * @return
	 */
	  @RequestMapping(value = "/dataSourceCompare",method = RequestMethod.POST)
	  public ModelAndView dataSourceCompare(String isChecked){
			Map<String, Object> okAttr = new HashMap<>();
			LinkedHashMap<String ,LinkedHashMap<String,String>>  metaDataSourceOne =   metaDataOneService.getMetaEntities();
			LinkedHashMap<String ,LinkedHashMap<String,String>>  metaDataSourceTwo =   metaDataTwoService.getMetaEntities();
			List<Map<String,Object>> resultList =new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> resultListTemp =new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> exceptionList =new ArrayList<Map<String,Object>>();
			
			String is_exception="0";
			//只有数据源one存在的表
			Map<String,String> onlyOneExistTableMap=new HashMap<String,String>();
			List<String> onlyOneExistTableList=new ArrayList<String>();
			for(Map.Entry<String ,LinkedHashMap<String,String>> entry:metaDataSourceOne.entrySet()) {
				if(!metaDataSourceTwo.containsKey(entry.getKey())) {
					onlyOneExistTableMap.put(entry.getKey(), entry.getKey());
					onlyOneExistTableList.add(entry.getKey());
				}else {
					is_exception="0";
					HashMap<String,Object> obj=new HashMap<String,Object>();
					obj.put("table_name", entry.getKey());
					
					LinkedHashMap<String,String> oneFieldMap=entry.getValue();
					obj.put("table_id1", oneFieldMap.get("table_id"));
					obj.put("table_label", oneFieldMap.get("table_label"));
					LinkedHashMap<String,String> twoFieldMap=metaDataSourceTwo.get(entry.getKey());
					obj.put("table_id2", twoFieldMap.get("table_id"));
					if(!oneFieldMap.get("table_id").equals(twoFieldMap.get("table_id"))) {
						is_exception="1";
						obj.put("reason","主键id不一致");
					}else {
						obj.put("reason","无");
					}
					this.compareField(obj,oneFieldMap, twoFieldMap,is_exception,isChecked);
					if("1".equals(obj.get("is_exception"))) {
						exceptionList.add(obj);
					}else {
						resultListTemp.add(obj);
					}
				}
			}
			resultList.addAll(exceptionList);
			resultList.addAll(resultListTemp);
			//只有数据源two存在的表
			Map<String,String> onlyTwoExistTableMap=new HashMap<String,String>();
			List<String> onlyTwoExistTableList=new ArrayList<String>();
			for(String key:metaDataSourceTwo.keySet()) {
				if(!metaDataSourceOne.containsKey(key)) {
					onlyTwoExistTableMap.put(key, key);
					onlyTwoExistTableList.add(key);
				}
			}
			okAttr.put("resultList", resultList);
			okAttr.put("onlyOneExistTableList", onlyOneExistTableList);
			okAttr.put("onlyTwoExistTableList", onlyTwoExistTableList);
			okAttr.put("isChecked", isChecked);
		    ModelAndView mv = new ModelAndView("index",okAttr);   
	    return mv;  
	  }
	
	 private void compareField(HashMap<String,Object> obj,LinkedHashMap<String,String> oneFieldMap,LinkedHashMap<String,String> twoFieldMap,String is_exception,String isChecked) {
		 	String is_exceptiontemp="";
			//只有数据源one中的表存在的字段
			Map<String,String> onlyOneExistFieldMap=new HashMap<String,String>();
			List<String> onlyOneExistFieldList=new ArrayList<String>();
			List<Map<String,String>> exceptionfieldList=new ArrayList<Map<String,String>>();
			List<Map<String,String>> fieldList=new ArrayList<Map<String,String>>();
			for(Map.Entry<String, String> entry:oneFieldMap.entrySet()) {
				if(entry.getKey().indexOf("~field_name")>-1) {
					if(!twoFieldMap.containsKey(entry.getKey())){//只有数据源one中的表存在的字段
						is_exception="1";
						onlyOneExistFieldMap.put(entry.getKey(), entry.getKey());
						onlyOneExistFieldList.add( entry.getKey());
					}else {//同时在两个数据源中
						String field_name=entry.getValue();
						String field_label=oneFieldMap.get(field_name+"~field_label");
						String field_type1=oneFieldMap.get(field_name+"~field_type");
						String field_type2=twoFieldMap.get(field_name+"~field_type");
						String field_updated1=oneFieldMap.get(field_name+"~field_updated");
						String field_updated2=twoFieldMap.get(field_name+"~field_updated");
						Map<String,String> fieldMap=new HashMap<>();
						fieldMap.put("field_name", field_name);
						fieldMap.put("field_label", field_label);
						fieldMap.put("field_type1", field_type1);
						fieldMap.put("field_type2", field_type2);
						if("1".equals(isChecked)&&field_updated1.equals(field_updated2)) {//开启快速比较
							is_exceptiontemp="0";
							fieldMap.put("reason", "无");
							fieldList.add(fieldMap);
						}else {
							//现在只是比较字段类型不同 此处以后可扩展
							if(!field_type1.equals(field_type2)) {
								is_exception="1";
								is_exceptiontemp="1";
								fieldMap.put("reason", "数据源1类型【"+field_type1+"】与数据源2类型【"+field_type2+"】不一致");
								exceptionfieldList.add(fieldMap);
							}else {
								is_exceptiontemp="0";
								fieldMap.put("reason", "无");
								fieldList.add(fieldMap);
							}
						}
						fieldMap.put("is_exception", is_exceptiontemp);
					}
					
				}
			}
			obj.put("fieldList", fieldList);
			obj.put("exceptionfieldList", exceptionfieldList);
			obj.put("onlyOneExistFieldList", onlyOneExistFieldList);
			//只有数据源two存在的表
			Map<String,String> onlyTwoExistFieldMap=new HashMap<String,String>();
			List<String> onlyTwoExistFieldList=new ArrayList<String>();
			for(String key:twoFieldMap.keySet()) {
				if(key.indexOf("~field_name")>-1&&!oneFieldMap.containsKey(key)) {
					onlyTwoExistFieldMap.put(key, key);
					onlyTwoExistFieldList.add(key);
					is_exception="1";
				}
			}
			obj.put("onlyTwoExistFieldList", onlyTwoExistFieldList);
			obj.put("is_exception", is_exception);
	 }
		
	
}
