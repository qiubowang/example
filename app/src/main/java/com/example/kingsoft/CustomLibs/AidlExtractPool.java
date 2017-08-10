package com.example.kingsoft.CustomLibs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.kingsoft.aidllibs.ILibraryInfoAidlInterface;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class AidlExtractPool {
    public static final int BOOK_INDEX = 0;
    public static final int USER_INDEX = 0;
    private Context mContext = null;
    ILibraryInfoAidlInterface libraryInfoAid = null;
    private Handler mHandler = null;

    public AidlExtractPool(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
    }

    public void startService(){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.example.myservice", "com.example.myservice.BookLibraryManagerService");
        intent.setComponent(componentName);
        mContext.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public IBinder queryBinder(int binderIndex){
            try {
                return libraryInfoAid.queryLibraryInfo(binderIndex);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            libraryInfoAid = ILibraryInfoAidlInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
