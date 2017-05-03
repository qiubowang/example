package com.example.kingsoft.CustomAdapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingsoft on 2017/4/22.
 */

public class MyListView5 extends ListActivity {

    private List<Map<String, Object>> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Debug.waitForDebugger();
        super.onCreate(savedInstanceState);
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "G1");
        map.put("info", "google 1");
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

    // ListView 中某项被选中后的逻辑
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.v("MyListView4-click", (String)mData.get(position).get("title"));
    }

    /**
     * listview中点击按键弹出对话框
     */
    public void showInfo(){
        new AlertDialog.Builder(this)
                .setTitle("我的listview")
                .setMessage("介绍...")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }}).show();
    }

    /** 当Listie有大量的数据需要加载的时候，会占据大量内存，影响性能，这时候   就需要按需填充并重新使用    *view来减少对象的创建
     *最快的方式是定义一个Pewholder，将convex的tag设置为Pewholder,不为空时重新使用即可
     */
    public final class ViewHolder{
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button viewBtn;
    }

    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = mInflater.inflate(R.layout.vlist2, null);
                holder.img = (ImageView)convertView.findViewById(R.id.imageView1);
                holder.title = (TextView)convertView.findViewById(R.id.title);
                holder.info = (TextView)convertView.findViewById(R.id.info);
                holder.viewBtn = (Button)convertView.findViewById(R.id.view_button);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
            holder.title.setText((String)mData.get(position).get("title"));
            holder.info.setText((String)mData.get(position).get("info"));
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showInfo();
                }});
            return convertView;
        }
    }
}
