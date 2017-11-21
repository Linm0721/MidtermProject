package com.example.sam.midterm_project;

/**
 * Created by sam on 2017/11/20.
 */

import java.io.Serializable;

public class Info implements Serializable{

    private String name;//姓名
    private String native_place;//籍贯
    private String sex;//性别
    private String date;//生卒
    private int background;//头像背景图
    private String message;//其它信息

    public Info(String name,String native_place,String sex,String date,int background,String message){
        this.name = name;
        this.native_place = native_place;
        this.sex = sex;
        this.date = date;
        this.background = background;
        this.message = message;
    }

    public String getName(){
        return  name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getNative_place(){
        return  native_place;
    }
    public void setNative_place(String native_place){
        this.native_place = native_place;
    }
    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex = sex;
    }
    public String getDate(){return  date;}
    public void setDate(String date){
        this.date = date;
    }
    public char get_first_letter(){
        char first = name.charAt(0);
        if (first >= 97 && first <= 122) {
            first -= 32;
        }
        return first;//得到首字母
    }
    public int getBackground() {
        return background;
    }
    public void setBackground(int background){
        this.background = background;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

}

