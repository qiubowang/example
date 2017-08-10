package com.example.kingsoft.aidllibs;

import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class LibraryInfoStub extends ILibraryInfoAidlInterface.Stub {
    public final static int BOOK_INDEX = 0;
    public final static int USER_INDEX = 1;

    private static IBinder bookStub = new BookStub();
    private static IBinder userStub = new UserStub();
    @Override
    public IBinder queryLibraryInfo(int binderIndex) throws RemoteException {
        if (binderIndex == BOOK_INDEX) {
            return bookStub;
        } else if (binderIndex == USER_INDEX) {
            return userStub;
        }
        return null;
    }
}
