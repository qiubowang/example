package com.example.kingsoft.aldllibs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class User implements Parcelable {
    private String mName;
    private int mAge;
    private String mAddress;
    private String mSex;

    public User(){};

    public User(Parcel parcelIn){
        mName = parcelIn.readString();
        mAge = parcelIn.readInt();
        mAddress = parcelIn.readString();
        mSex = parcelIn.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeInt(mAge);
        parcel.writeString(mAddress);
        parcel.writeString(mSex);
    }

    public final static Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[0];
        }
    };
}
