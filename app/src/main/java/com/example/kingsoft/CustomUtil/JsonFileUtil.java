package com.example.kingsoft.CustomUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用于操作json文件
 * 
 * @author qiubowang
 *
 */
public class JsonFileUtil {
	private File mJSonFile;
	
	public JsonFileUtil(File file) {
		mJSonFile = file;
	}

	public ArrayList<Long> getPrevs(String tag) throws IOException,
			JSONException {
		JSONObject object = JSONTool.read(mJSonFile);
		JSONArray prevsArray = object.getJSONArray(getClass().getSimpleName()
				+ tag);
		int n = prevsArray.length();
		ArrayList<Long> prevs = new ArrayList<Long>(n + 1); // 一般之后要再加一个元素,
															// 所以多申请一个
		for (int i = 0; i < n; ++i)
			prevs.add(prevsArray.getLong(i));
		return prevs;
	}

	public ArrayList<ArrayList<Long>> getPrevsResults(String tag)
			throws IOException, JSONException {
		JSONObject object = JSONTool.read(mJSonFile);
		JSONArray prevsArray = object.getJSONArray(getClass().getSimpleName()
				+ tag);
		int n = prevsArray.length();
		ArrayList<ArrayList<Long>> prevs = new ArrayList<ArrayList<Long>>(n + 1); // 一般之后要再加一个元素,
																					// 所以多申请一个
		for (int i = 0; i < n; ++i) {
			JSONArray resultArray = prevsArray.getJSONArray(i);
			int len = resultArray.length();
			ArrayList<Long> results = new ArrayList<Long>(len);
			for (int j = 0; j < len; ++j) {
				results.add(resultArray.getLong(j));
			}
			prevs.add(results);
		}
		return prevs;
	}

	public void putPrevs(ArrayList<Long> prevs, String tag) throws IOException,
			JSONException {
		File f = mJSonFile;
		JSONObject object = null;
		if (f.exists())
			object = JSONTool.read(f);
		else
			object = new JSONObject();
		object.put(getClass().getSimpleName() + tag, new JSONArray(prevs));
		JSONTool.write(object, mJSonFile);
	}

	public void putPrevsResults(ArrayList<ArrayList<Long>> prevs, String tag)
			throws IOException, JSONException {
		File f = mJSonFile;
		JSONObject object = null;
		if (f.exists())
			object = JSONTool.read(f);
		else
			object = new JSONObject();

		JSONArray result = new JSONArray();

		int n = prevs.size();
		for (int i = 0; i < n; ++i) {
			result.put(i, new JSONArray(prevs.get(i)));
		}

		object.put(getClass().getSimpleName() + tag, result);
		JSONTool.write(object, mJSonFile);
	}

	
	public void clearObject(String tag) throws IOException, JSONException{
		File f = mJSonFile;
		JSONObject object = null;
		if (f.exists())
			object = JSONTool.read(f);
		else
			object = new JSONObject();

		boolean isNull = object.isNull(tag);
		if (!isNull) {
			object.remove(tag);
		}
	}
}
