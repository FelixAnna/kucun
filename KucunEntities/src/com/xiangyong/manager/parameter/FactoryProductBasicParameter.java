package com.xiangyong.manager.parameter;

import java.math.BigDecimal;

public class FactoryProductBasicParameter {
    private int factoryId;
    private int productBasicId;
    private String link;
    private BigDecimal marketPrice;
    private BigDecimal singlePrice;

    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public int getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(int productBasicId) {
        this.productBasicId = productBasicId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }
}
