package com.xiangyong.manager.core.exception;

public class DataNotFoundException extends RuntimeException {
    private int id;

    public DataNotFoundException(int id){
        this.id =id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
