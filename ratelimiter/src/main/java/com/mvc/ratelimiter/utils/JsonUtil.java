package com.mvc.ratelimiter.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @author kishore
 * 
 * Utility Class for JSON processing...
 *
 */
public class JsonUtil {

	public static JsonObject getJsonObjectFromstring(String res) {
		JsonObject jsonObj = (JsonObject) new JsonParser().parse(res);
		return jsonObj;
	}
}
