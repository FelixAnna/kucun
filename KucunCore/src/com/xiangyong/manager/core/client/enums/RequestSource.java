package com.xiangyong.manager.core.client.enums;

/**
 * Created by Rex.Lei on 2017/8/30.
 */
public enum RequestSource {

    ANDROID(1,"android"),

    IOS(2,"ios"),

    PC(3,"pc"),

    WEIXIN(4,"weixin");

    private int value;

    private String name;

    RequestSource(int value,String name){
        this.value = value;
        this.name = name;
    }

    public static RequestSource valueOf(int value){
        RequestSource result = null;
        for (RequestSource item:values()) {
            if(item.getValue() == value){
                result =  item;
                break;
            }
        }
        return result;
    }

    public static RequestSource getByName(String name){
        RequestSource result = null;
        for (RequestSource item:values()) {
            if(item.getName() == name){
                result =  item;
                break;
            }
        }
        return result;
    }

    public static RequestSource getByAppName(String name){
        if(ANDROID.getName().equals(name)){
            return RequestSource.ANDROID;
        }else if(IOS.getName().equals(name)){
            return RequestSource.IOS;
        }else{
            return null;
        }
    }

    public boolean isApp(){
        return this == RequestSource.ANDROID || this == RequestSource.IOS;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
