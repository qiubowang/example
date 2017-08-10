package com.example.kingsoft.ActivityLibs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomUtil.CustomAsyncTask;

/**
 * Created by kingsoft on 2017/6/5.
 */

public class DownloadPicActivity extends Activity implements View.OnClickListener{
    private static final String DOWNLOAD_ADDR = "http://developer.android.com/images/home/kk-hero.jpg";

    Button downloadButton = null;
    ImageView imageView = null;
    ProgressDialog progressDialog = null;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.async_download_pic);
        imageView = (ImageView)findViewById(R.id.download_image);
        downloadButton = (Button)findViewById(R.id.download_pic_button);
        downloadButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download_pic_button){
            CustomAsyncTask customAsyncTask = new CustomAsyncTask(progressDialog, imageView);
            customAsyncTask.execute(DOWNLOAD_ADDR);
        }
    }
}
