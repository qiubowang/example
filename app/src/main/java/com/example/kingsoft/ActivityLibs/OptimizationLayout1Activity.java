package com.example.kingsoft.ActivityLibs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;
import android.widget.Button;

import com.example.kingsoft.CustomAdapter.R;


public class OptimizationLayout1Activity extends AppCompatActivity {
    private Button mButton = null;
    private ViewStub mViewStub = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optimization_layout);

        mButton = (Button)findViewById(R.id.opt_button);
        mButton.setOnClickListener((v -> {
//            Debug.waitForDebugger();
            mViewStub = (ViewStub)findViewById(R.id.opt_view_stub);
            mViewStub.inflate();
        }));
    }
}
