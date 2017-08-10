// IUsersAidlInterface.aidl
package com.example.kingsoft.aidllibs;

import com.example.kingsoft.aidllibs.User;

interface IUsersAidlInterface {
    List<User> getUsers();
    void addUser(in User user);
}
