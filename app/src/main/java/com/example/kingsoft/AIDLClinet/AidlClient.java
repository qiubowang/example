package com.example.kingsoft.AIDLClinet;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.kingsoft.aldllibs.IMyAidlInterfaceStudent;

/**
 * Created by kingsoft on 2017/3/7.
 */
public class AidlClient {


    IMyAidlInterfaceStudent mBinder = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinder = IMyAidlInterfaceStudent.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBinder = null;
        }
    };
}
