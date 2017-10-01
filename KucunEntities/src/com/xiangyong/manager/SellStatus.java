package com.xiangyong.manager;

public enum  SellStatus {
    Ready(0, "出售中"),
    Cancelled(1, "已取消"),
    Deal(2, "已完成");

    private int value;
    private String name;
    SellStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public static SellStatus valueOf(int value){
        SellStatus result = null;
        for (SellStatus item:values()) {
            if(item.getValue() == value){
                result =  item;
                break;
            }
        }
        return result;
    }

    public static SellStatus getByName(String name){
        SellStatus result = null;
        for (SellStatus item:values()) {
            if(item.getName() == name){
                result =  item;
                break;
            }
        }
        return result;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
