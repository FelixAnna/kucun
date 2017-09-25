package com.xiangyong.manager.core.form;

import java.io.Serializable;

public class PageForm implements Serializable{

    private int pageNum = 1;

    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
