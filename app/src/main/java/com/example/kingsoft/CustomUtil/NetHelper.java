package com.example.kingsoft.CustomUtil;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by kingsoft on 2017/6/6.
 */

public class NetHelper {
    private Context mContext = null;
    private byte[] mReponseBody = null;
    private String mReponseHeader = null;
    private byte[] mRequestBody = null;

    public NetHelper(Context context){
        mContext = context;
    }

    public void HttpHelper(String httpUrl){
        try {
            //发送请求
            URL url = new URL(httpUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void HttpGetHelper(HttpURLConnection httpURLConnection){
        if (null == httpURLConnection)
            return;
        try {
            //设置请求方式，GET或者POST，默认为GET可以不显示设置
            httpURLConnection.setRequestMethod("GET");
            //开取输入流，默认为true可以不显示设置
            httpURLConnection.setDoInput(true);
            //设置请求头信息，参考restrictedHeaders
            //httpURLConnection.setRequestProperty("Content-Length","");
            //设置禁用网络缓存
            httpURLConnection.setUseCaches(false);
           //链接服务器，可以不用显示链接 getInputStreae()中会调用connet()
            httpURLConnection.connect();
            //getInputStream之后服务端才会收到请求，并且客户端阻塞似的获取服务器数据
            InputStream inputStream = httpURLConnection.getInputStream();
            //请求成功
            if (httpURLConnection.getResponseCode() == 200) {
                //获取请求的数据
                mReponseBody = getBytesByInputStream(inputStream);
                //获取响应的头信息
                mReponseHeader = getReponseHeader(httpURLConnection);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  HttpPost(HttpURLConnection httpURLConnection){
        try {
            //设置请求方式，GET或者POST，默认为GET,POST必须显示设置
            httpURLConnection.setRequestMethod("POST");
            //doOutput默认为false,必须显示设置为true
            httpURLConnection.setDoOutput(true);
            //doInput默认为true，可以不设置
            httpURLConnection.setDoInput(true);
            //强制不缓存
            httpURLConnection.setUseCaches(false);
            //获取输出流
            OutputStream outputStream = httpURLConnection.getOutputStream();
            //获取请求体
            mRequestBody = new String("name=bobo&age=20").getBytes("UTF-8");
            //如果是XML文件
            //mRequestBody = getDataByAssert("temple.xml");
            //如果是JSON数据
            //mRequestBody =  getDataByAssert("temple.json");
            //写入请求体
            outputStream.write(mReponseBody);
            //刷新和关闭输出流
            outputStream.flush();
            outputStream.close();
            //调用getInputSteam()讲请求发送给服务器，并且客户端阻断似的获取数据
            InputStream inputStream = httpURLConnection.getInputStream();
            //将从服务器获取的数据转换为byte数组
            mReponseBody = getBytesByInputStream(inputStream);
            //获取服务器返回的header
            mReponseHeader = getReponseHeader(httpURLConnection);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**•在方法getBytesByInputStream中，通过输入流的read方法得到字节数组，read方法是阻塞式的，每read一次，
     * 其实就是从服务器上下载一部分数据，直到将服务器的输出全部下载完成，这样就得到响应体responseBody了。
     *
     * @param is
     * @return
     */
    private byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    private String getReponseHeader(HttpURLConnection connection){
        Map<String,List<String>> headerFields =  connection.getHeaderFields();
        if (null == headerFields || headerFields.size() <= 0)
            return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, size = headerFields.size(); i < size; i++){
            stringBuilder.append(connection.getHeaderFieldKey(i))
                         .append(":")
                         .append(connection.getHeaderField(i))
                         .append("\n");
        }
        return stringBuilder.toString();
    }

    /**从资源文件assert中读取文件
     *
     */
    public byte[] getDataByAssert(String fileName){
        AssetManager assetManager = mContext.getAssets();
        byte[] datas = null;
        try {
            InputStream inputStream = assetManager.open(fileName);
            datas = getBytesByInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }

    public String byte2String(byte[] datas){
        String strData = null;
        try {
            strData =  new String(datas, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strData;
    }

    //restrictedHeaders 是header的类型，setRequestProperty可以选择以下系统给定的类型，或者自定义类型
    private static final String[] restrictedHeaders = {
	/* Restricted by XMLHttpRequest2 */
            //"Accept-Charset",
            //"Accept-Encoding",
            "Access-Control-Request-Headers",
            "Access-Control-Request-Method",
            "Connection", /* close is allowed */
            "Content-Length",
            //"Cookie",
            //"Cookie2",
            "Content-Transfer-Encoding",
            //"Date",
            "Expect",
            "Host",
            "Keep-Alive",
            "Origin",
            // "Referer",
            // "TE",
            "Trailer",
            "Transfer-Encoding",
            "Upgrade",
            //"User-Agent",
            "Via"
    };

    public static List<Object> getJSonObject(String strData){
        try {
//            JSONObject jsonObject = new JSONObject(strData);
            JSONArray jsonArray = new JSONArray(strData);
            for (int i = 0, length = jsonArray.length(); i < length; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int intVal = jsonObject.getInt("aa");
                String strVal = jsonObject.getString("bb");

            }

            //

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
