package com.example.kingsoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kingsoft.CustomAdapter.MyBaseAdapter;
import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.aidllibs.Book;
import com.example.kingsoft.aidllibs.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kingsoft on 2017/6/20.
 */

public class CustomBaseAdapter extends BaseAdapter {
    private List<Object> mDatas = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;

    public CustomBaseAdapter(){};

    public CustomBaseAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder = null;
        if (null == convertView){
            viewHolder = new ViewHolder();
            View view = mInflater.inflate(R.layout.aidl_listview, null);
            viewHolder.bookName = (TextView) view.findViewById(R.id.book_name);
            viewHolder.bookAuthor = (TextView) view.findViewById(R.id.book_author);
            viewHolder.bookCategory = (TextView) view.findViewById(R.id.book_category);
            viewHolder.bookPrice = (TextView) view.findViewById(R.id.book_price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Object data = mDatas.get(position);
        if (data instanceof Book){
            Book book = (Book)data;
            viewHolder.bookName.setText(book.getBookName());
            viewHolder.bookAuthor.setText(book.getAuthor());
            viewHolder.bookCategory.setText(book.getCategory());
            viewHolder.bookPrice.setText(String.valueOf(book.getPrice()));
        }else if (data instanceof User){

        }

        return convertView;
    }

    public void addDatas(List<Map<String, String>> datas){
        if (null == mDatas)
            mDatas = new ArrayList();
        mDatas.addAll(datas);
    }

    public void addData(Map<String, String> data){
        mDatas.add(data);
    }

    /**ViewHolder 就是为了缓存ListView中的相关控件，以便循环利用
     *
     */
    public class ViewHolder{
        public TextView bookName;
        public TextView bookAuthor;
        public TextView bookCategory;
        public TextView bookPrice;
    }
}
