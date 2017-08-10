package com.example.kingsoft.ObjectLibs;

import com.example.kingsoft.CustomUtil.GsonUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Until;

/**
 * Created by qiubowang on 2017/6/7
 *
 * Expose(serialize = false,deserialize = false)本注解指定哪些字段可以序列化或反序列化，但是设置GsonBuilder.excludeFieldsWithoutExposeAnnotation才有用
 */

public class Person {
    @Expose(serialize = true,deserialize = false)
    private String mName = null;
    @Expose(serialize = true)
    private int mAge = -1;
    @Expose(serialize = false)
    private String mAddress = null;
    @Expose(serialize = false)
    private String mSchool = null;
    @SerializedName("iD")
    @Expose(serialize = false)
    @Until(1.1)
    private String mID = null;

    @GsonUtil.ICustomIgnoreAnnotation
    public long mobilePhone = 1888888888;

    public Person(){

    }

    public void setName(String name){
        this.mName = name;
    }

    public void setAge(int age){
        this.mAge = age;
    }

    public void setAddress(String address){
        this.mAddress = address;
    }

    public void setSchool(String school){
        this.mSchool = school;
    }

    public void setID(String id){
        this.mID = id;
    }

    public String getName(){
        return mName;
    }

    public int getAge(){
        return this.mAge;
    }

    public String getAddress(){
        return this.mAddress;
    }

    public String getSchool(){
        return this.mAddress;
    }

    public String getID(){
        return this.mID;
    }
}
