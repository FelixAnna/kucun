package com.xiangyong.manager.core.result;

import com.xiangyong.manager.core.dto.BaseDto;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> extends BaseDto{

    /**
     * 每页默认10条数据
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总数据数
     */
    private long totalRows;

    /**
     * 每页的起始行数
     */
    private int pageStartRow;

    /**
     * 每页显示数据的终止行数
     */
    private int pageEndRow;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage;

    /**
     * 数据
     */
    private List<T> result;

    public PageResult(int pageSize, int currentPage, int totalPages, long totalRows, int pageStartRow, int pageEndRow, List<T> result) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalRows = totalRows;
        this.pageStartRow = pageStartRow;
        this.pageEndRow = pageEndRow;
        this.result = result;
        this.hasNextPage = currentPage < totalPages;
        this.hasPreviousPage = 1 < currentPage && currentPage < totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
