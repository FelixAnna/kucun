package com.xiangyong.manager.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProductBasicAndDetailsDto
{
    //商品基本信息
    private int productBasicId;
    private String productName;
    private String link;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private boolean isDeleted;
    private int userId;

    //owner 信息
    private String userName;

    //商品详情
    private ArrayList<ProductDetailDTO> productDetailDTOS;

    public int getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(int productBasicId) {
        this.productBasicId = productBasicId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<ProductDetailDTO> getProductDetailDTOS() {
        return productDetailDTOS;
    }

    public void setProductDetailDTOS(ArrayList<ProductDetailDTO> productDetailDTOS) {
        this.productDetailDTOS = productDetailDTOS;
    }
}
