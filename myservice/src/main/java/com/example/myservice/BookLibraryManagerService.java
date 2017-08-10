package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.kingsoft.aidllibs.LibraryInfoStub;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class BookLibraryManagerService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IBinder mBinder = new LibraryInfoStub();
}
