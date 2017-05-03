package com.example.kingsoft.commenclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kingsoft on 2017/3/6.
 */
public class SudentParcelable  implements Parcelable{

    private String name;
    private int age;
    private String sex;
    public SudentParcelable(String name, String sex, int age){
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(sex);
        parcel.writeInt(age);
    }

    public static final Parcelable.Creator<SudentParcelable> studentParcel = new ClassLoaderCreator<SudentParcelable>() {
        @Override
        public SudentParcelable createFromParcel(Parcel parcel) {
            return new SudentParcelable(parcel.readString(),parcel.readString(),parcel.readInt());
        }

        @Override
        public SudentParcelable[] newArray(int i) {
            return new SudentParcelable[i];
        }

        @Override
        public SudentParcelable createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return null;
        }
    };
}
