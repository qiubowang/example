package com.example.kingsoft.CustomUtil;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.ParcelUuid;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by kingsoft on 2017/6/5.
 */

public class CustomAsyncTask extends AsyncTask<String, Integer, byte[]> {
    private ProgressDialog mProgressDialog = null;
    private ImageView mImageView = null;

    public CustomAsyncTask(ProgressDialog progressDialog, ImageView imageView){
        mProgressDialog = progressDialog;
        mImageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected byte[] doInBackground(String... params) {
        if (null == params || params.length <= 0 ) {
           return null;
        }
        //http请求
        try {
//            Debug.waitForDebugger();
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);

            if (httpURLConnection.getResponseCode() == 200){
                InputStream inputStream = httpURLConnection.getErrorStream();
                return dealResponseResult(inputStream);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new byte[]{};
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(byte[] resultData) {
        if (null == resultData | resultData.length <= 0)
            return;
        super.onPostExecute(resultData);
        Bitmap bitmap = BitmapFactory.decodeByteArray(resultData,0, resultData.length);
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /*
 * Function  :   处理服务器的响应结果（将输入流转化成字符串）
 * Param     :   inputStream服务器的响应输入流
 */
    public  byte[] dealResponseResult(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
