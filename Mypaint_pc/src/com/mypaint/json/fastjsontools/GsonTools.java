package com.mypaint.json.fastjsontools;
import com.google.gson.Gson;
public class GsonTools {

	public GsonTools() {
		// TODO Auto-generated constructor stub
	}
	public static <T> T getCoordinate(String jsonString,Class<T> cls){
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
}
