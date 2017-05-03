package com.example.kingsoft.CustomAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;

/**
 * Created by kingsoft on 2017/4/21.
 */

public class MyListView4 extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Debug.waitForDebugger();
        super.onCreate(savedInstanceState);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(this);
        setListAdapter(myBaseAdapter);
    }


}
