package com.example.kingsoft.CustomAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kingsoft.studyproject.TurnPageAnimation;

/**
 * Created by kingsoft on 2017/4/25.
 */

public class FirstActivity extends Activity  implements View.OnClickListener{
    private Button myButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        myButton = (Button)this.findViewById(R.id.button);
        myButton.setOnClickListener(this);
        Log.d("FirstActivity", "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("FirstActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("FirstActivity", "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("FirstActivity", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("FirstActivity", "onPause");
    }

    @Override
    protected void onStop() {
//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        super.onStop();
        Log.d("FirstActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("FirstActivity", "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("FirstActivity", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("FirstActivity", "onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
//            newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
//        else
//            newConfig.orientation = Configuration.ORIENTATION_LANDSCAPE;

        super.onConfigurationChanged(newConfig);
        Log.d("FirstActivity", "onConfigurationChanged");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button){
            Intent intent = new Intent(this, SecondActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            startActivity(intent);
        }
    }
}
