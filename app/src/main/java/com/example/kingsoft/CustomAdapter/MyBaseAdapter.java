package com.example.kingsoft.CustomAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.kingsoft.studyproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingsoft on 2017/4/21.
 */

public class MyBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private List<Map<String, Object>> mData;
    private Context mContext;

    public MyBaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = getData();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.vlist2,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.info = (TextView)convertView.findViewById(R.id.info);
            holder.viewBtn = (Button) convertView.findViewById(R.id.view_button);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.imageView.setBackgroundResource((Integer) mData.get(i).get("img"));
        holder.title.setText((String)mData.get(i).get("title"));
        holder.info.setText((String) mData.get(i).get("info"));
        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfo();
            }
        });
        return convertView;
    }

    public final class ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }

    private List<Map<String, Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title","G1");
        map.put("title","Goolgle 1");
        map.put("img", R.drawable.ppt_phone_remote_operator_run);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "G2");
        map.put("info", "google 2");
        map.put("img", R.drawable.ppt_phone_remote_operator_run);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "G3");
        map.put("info", "google 3");
        map.put("img", R.drawable.ppt_phone_remote_operator_run);
        list.add(map);
        return list;
    }

    public void showInfo(){
        new AlertDialog.Builder(mContext)
                .setTitle("触发了点击事件")
                .setMessage("介绍BaseAdapter")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
