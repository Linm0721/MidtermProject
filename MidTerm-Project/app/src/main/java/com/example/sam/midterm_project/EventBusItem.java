package com.example.sam.midterm_project;

/**
 * Created by sam on 2017/11/20.
 */


public class EventBusItem{

    private String name;
    private String price;
    public EventBusItem(String name,String price){
        this.name = name;
        this.price = price;
    }
    public String get_first_letter(){
        return String.valueOf(name.charAt(0)).toUpperCase();
    }
    public String get_name(){
        return name;
    }
    public String get_price(){
        return price;
    }

}