package com.example.kingsoft.ActivityLibs;

import android.app.Activity;
import android.os.Bundle;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomWidget.WaterRippleFrameLayout;

public class WaterRippleActivity extends Activity {
    WaterRippleFrameLayout waterRippleFrameLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_ripple_layout);
        waterRippleFrameLayout = (WaterRippleFrameLayout)findViewById(R.id.water_repple_framelayout);
        waterRippleFrameLayout.init();
    }
}
