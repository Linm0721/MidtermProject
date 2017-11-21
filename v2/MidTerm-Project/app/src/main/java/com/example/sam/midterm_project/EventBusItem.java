package com.example.sam.midterm_project;

/**
 * Created by sam on 2017/11/20.
 */


public class EventBusItem{

    private String name;//名字
    private int background;//头像
    public EventBusItem(String name,int background){
        this.name = name;
        this.background = background;
    }
    public String get_name(){
        return name;
    }
    public int get_background(){
        return background;
    }

}