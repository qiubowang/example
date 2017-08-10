package com.example.kingsoft.ActivityLibs;

import android.app.Activity;
import android.os.Bundle;

import com.example.kingsoft.CustomAdapter.R;


public class CustomWidgetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_button);
    }
}
