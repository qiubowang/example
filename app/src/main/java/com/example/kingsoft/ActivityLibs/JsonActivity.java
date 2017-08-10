package com.example.kingsoft.ActivityLibs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomUtil.CustomJson;

/**
 * Created by kingsoft on 2017/6/7.
 */

public class JsonActivity extends Activity implements View.OnClickListener {
    private Button mJsonButton = null;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        setContentView(R.layout.json_layout);
        mJsonButton = (Button) findViewById(R.id.json_button);
        mJsonButton.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.json_button){
//            CustomJson.publicJson();
        }
    }
}
