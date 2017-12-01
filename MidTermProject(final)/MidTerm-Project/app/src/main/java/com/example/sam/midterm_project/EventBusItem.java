package com.example.sam.midterm_project;

/**
 * Created by sam on 2017/11/20.
 */


public class EventBusItem{

    private String name;
    private int background;
    public EventBusItem(String name,int background){
        this.name = name;
        this.background = background;
    }
    public String get_first_letter(){
        return String.valueOf(name.charAt(0)).toUpperCase();
    }
    public String get_name(){
        return name;
    }
    public int get_background(){
        return background;
    }

}