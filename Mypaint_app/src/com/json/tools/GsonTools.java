package com.json.tools;

import com.google.gson.Gson;

public class GsonTools {

	public GsonTools() {
		// TODO Auto-generated constructor stub
	}

	public static String createJsonString(Object value) {
		Gson gson = new Gson();
		return gson.toJson(value);
	}
}
