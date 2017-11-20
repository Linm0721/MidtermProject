package com.example.sam.midterm_project;

/**
 * Created by ACER on 2017/11/20.
 */

public class EventBusForEdit {

    private String name;
    private String native_place;//籍贯
    private String sex;//性别
    private String date;//生卒
    private int background;
    private String message;
    public EventBusForEdit(String name,String native_place,String sex,String date,int background,String message){
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
    public void setSex(String clique){
        this.sex = clique;
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
