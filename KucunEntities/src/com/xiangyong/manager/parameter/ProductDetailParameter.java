package com.xiangyong.manager.parameter;

import java.math.BigDecimal;

public class ProductDetailParameter {
    private int productBasicId;
    private int colorId;
    private int sizeId;
    private int total;
    private int sold;
    private BigDecimal price;

    public int getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(int productBasicId) {
        this.productBasicId = productBasicId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
