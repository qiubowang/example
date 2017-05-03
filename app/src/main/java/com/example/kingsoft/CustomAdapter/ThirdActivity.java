package com.example.kingsoft.CustomAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by kingsoft on 2017/4/25.
 */

public class ThirdActivity extends Activity implements View.OnClickListener{
    private Button  mThirdButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        mThirdButton = (Button)this.findViewById(R.id.third_button);
        Log.d("ThirdActivity", "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ThirdActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ThirdActivity", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ThirdActivity", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ThirdActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ThirdActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ThirdActivity", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("ThirdActivity", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("ThirdActivity", "onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("ThirdActivity", "onConfigurationChanged");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.third_button){
            Intent intent = new Intent(this, SecondActivity.class);
            this.startActivity(intent);
        }
    }
}
