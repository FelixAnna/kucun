package com.xiangyong.manager.core.exception;

public class ForbiddenException extends RuntimeException {
    private int id;

    public ForbiddenException(int id){
        this.id =id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
