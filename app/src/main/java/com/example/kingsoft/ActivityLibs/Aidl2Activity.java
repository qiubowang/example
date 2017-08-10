package com.example.kingsoft.ActivityLibs;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.kingsoft.CustomAdapter.R;
import com.example.kingsoft.CustomLibs.AidlExtractPool;
import com.example.kingsoft.adapter.CustomBaseAdapter;
import com.example.kingsoft.aidllibs.Book;
import com.example.kingsoft.aidllibs.IBooksAidlInterface;

import java.util.List;


public class Aidl2Activity extends Activity {
    private ListView mListView = null;
    private BaseAdapter baseAdapter;
    AidlExtractPool aidlExtractPool = null;
    private Handler handler = null;
    private IBooksAidlInterface mBooksAidlInterface = null;
    private IBinder mBinder = null;
    private List<Book> books = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl2);

        if (null == handler)
            handler = new MyHandler();
        baseAdapter = new CustomBaseAdapter();
        mListView = (ListView)findViewById(R.id.aidl_list_view);
        aidlExtractPool = new AidlExtractPool(this, handler);
        aidlExtractPool.startService();

        new Thread(() -> {
            mBinder = aidlExtractPool.queryBinder(AidlExtractPool.BOOK_INDEX);
            if (null != mBinder){
                mBooksAidlInterface = IBooksAidlInterface.Stub.asInterface(mBinder);
                if (null != mBooksAidlInterface){
                    try {
                        books = mBooksAidlInterface.getBooks();
                        if (null != books){
                            Message message = handler.obtainMessage();
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

    }

    private class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                mListView.setAdapter(baseAdapter);
                baseAdapter.notifyDataSetChanged();
            }else {
                super.handleMessage(msg);
            }
        }
    }

}
