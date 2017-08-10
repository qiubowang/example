package com.example.kingsoft.aidllibs;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class UserStub extends IUsersAidlInterface.Stub {
    private List<User> mUsers = null;

    public UserStub(){
        if (null == mUsers)
            mUsers = new ArrayList<>();
        mUsers.add(new User("雷军", 45, "北京", "男"));
        mUsers.add(new User("马云", 45, "杭州", "男"));
        mUsers.add(new User("乔布斯", 100, "美国", "男"));
    }
    @Override
    public List<User> getUsers() throws RemoteException {
        return mUsers;
    }

    @Override
    public void addUser(User user) throws RemoteException {
        mUsers.add(user);
    }
}
