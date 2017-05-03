package com.example.kingsoft.AIDLService;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.kingsoft.aldllibs.IMyAidlInterfaceStudent;

/**
 * Created by kingsoft on 2017/3/7.
 */
public class AidlService extends Service{

    StudentBinder studentBinder = null;

    @Override
    public void onCreate() {
        super.onCreate();
        studentBinder = new StudentBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return studentBinder;
    }

    public class StudentBinder extends IMyAidlInterfaceStudent.Stub{

        @Override
        public void addVal(int a, int b) throws RemoteException {
            return;
        }
    }
}
