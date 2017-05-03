package com.example.kingsoft.CustomAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by kingsoft on 2017/4/25.
 */

public class SecondActivity extends Activity implements View.OnClickListener{
    public static String TAG = "SecondActivity";

    private Button mSecondButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        mSecondButton = (Button) this.findViewById(R.id.second_button);
        Log.d("SecondActivity", "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("SecondActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SecondActivity", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SecondActivity", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SecondActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SecondActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SecondActivity", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("SecondActivity", "onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("SecondActivity", "onConfigurationChanged");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.second_button){
            Intent intent = new Intent(this, ThirdActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            this.startActivity(intent);
        }
    }
}
