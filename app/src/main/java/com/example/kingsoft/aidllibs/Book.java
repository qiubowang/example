package com.example.kingsoft.aidllibs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kingsoft on 2017/6/19.
 */

public class Book implements Parcelable {
    private String mBookName;
    private String mAuthor;
    private String mCategory;
    private float mPrice;

    public Book(String bookName, String author, String category, float price){
        mBookName =bookName;
        mAuthor = author;
        mCategory = category;
        mPrice = price;
    }

    public Book(Parcel parcelIn){
        mBookName = parcelIn.readString();
        mAuthor = parcelIn.readString();
        mCategory = parcelIn.readString();
        mPrice = parcelIn.readFloat();
    }

    public String getBookName(){
        return this.mBookName;
    }

    public String getAuthor(){
        return this.mAuthor;
    }

    public String getCategory(){
        return this.mCategory;
    }

    public float getPrice(){
        return this.mPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mBookName);
        parcel.writeString(mAuthor);
        parcel.writeString(mCategory);
        parcel.writeFloat(mPrice);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[0];
        }
    };
}
