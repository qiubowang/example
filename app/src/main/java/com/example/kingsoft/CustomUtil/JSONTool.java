package com.example.kingsoft.CustomUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author qiubowang
 * @date 2015年3月23日
 */
public class JSONTool {
	public static JSONObject read(File f) throws IOException, JSONException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(f));
		int c;
		while ((c = reader.read()) >= 0)
			builder.append((char) c);
		reader.close();
		
		return new JSONObject(builder.toString());
	}
	
	public static void write(JSONObject object, File f) throws IOException, JSONException {
		FileWriter writer = new FileWriter(f);
		writer.write(object.toString(4));
		writer.close();
	}
}
