package com.example.kingsoft.CustomUtil;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * android采用的JSON包一般有三种内部的JSONObject,JSONArray等；谷歌的Gson；阿里巴巴的fastJson
 */

public class CustomJson {

    /**JSONObject,JSONArray解析
     * JSONObject是JSON对象的键值对，例如{"name":"bobo", "age":"10"}
     * JSONArrays是JSON的素组例如["aa", "bb". {"name":"bobo", "age":10}]
     * JSONObject 和 JSONArrays只能接收String对象，当然String对象必须是JSON结构的字符串，否则解析失败
     * JSONObject 和 JSONArrays对于解析接收来的json字符串比较方便，如果需要打包成json数据则比较繁琐，建议不要使用。
     */
    public static void orgJsonEncorder(){
        //JSONObject是JSON对象的键值对，例如{"name":"bobo", "age":"10"}
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "bobo");
            jsonObject.put("age", 100);
            String strResult = jsonObject.toString();
            Log.d("custom_json :", strResult);//strResult = "\"name\":\"bobo\", \"age\":\"10\"}";

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("person");
            jsonArray.put("books");
            jsonArray.put(jsonObject);
            String arrStr = jsonArray.toString();
            Log.d("custom_json :", arrStr);
//        输出结果:
//        [
//           "person",
//           "books",
//            {
//                  "name":"bobo",
//                  "age":"10"
//             }
//        ]
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] arg){

    }
}

