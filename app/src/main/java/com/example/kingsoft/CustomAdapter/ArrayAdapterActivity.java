package com.example.kingsoft.CustomAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kingsoft on 2017/4/18.
 */

public class ArrayAdapterActivity extends Activity{
   private ListView mListView = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListView = new ListView(this);
        List<String> datas = Arrays.asList(new String[]{"测试数据一"});
        datas.add("测试数据二");
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,datas));
        setContentView(mListView);
    }

    public List<String> getDatas(){
        List<String> datas = Arrays.asList(new String[]{"测试数据一","测试数据二","测试数据三"});
        return  datas;
    }
}
