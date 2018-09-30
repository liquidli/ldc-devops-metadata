package com.abrainyun.ldc.devops.metadata.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private static Gson gson = null;

	static {
		gson = new GsonBuilder().create();

	}

	public static String stringify(Object object) {

		try {
			return gson.toJson(object);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> T parse(String json, Class<T> clazz) {

		if (json == null || json.length() == 0) {
			return null;
		}

		try {
			return gson.fromJson(json, clazz);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return null;
	}
}
