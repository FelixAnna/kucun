package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "factory_product_basic", schema = "kucun", catalog = "")
public class FactoryProductBasicEntity {
    private int factoryProductBasicId;
    private int factoryId;
    private int productBasicId;
    private String link;
    private BigDecimal marketPrice;
    private BigDecimal singlePrice;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private boolean isDeleted;

    @Id
    @Column(name = "factory_product_basic_id", nullable = false)
    public int getFactoryProductBasicId() {
        return factoryProductBasicId;
    }

    public void setFactoryProductBasicId(int factoryProductBasicId) {
        this.factoryProductBasicId = factoryProductBasicId;
    }

    @Basic
    @Column(name = "factory_id", nullable = false)
    public int getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    @Basic
    @Column(name = "product_basic_id", nullable = false)
    public int getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(int productBasicId) {
        this.productBasicId = productBasicId;
    }

    @Basic
    @Column(name = "link", nullable = true, length = 1024)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Basic
    @Column(name = "market_price", nullable = false, precision = 2)
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    @Basic
    @Column(name = "single_price", nullable = false, precision = 2)
    public BigDecimal getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(BigDecimal singlePrice) {
        this.singlePrice = singlePrice;
    }

    @Basic
    @Column(name = "created_time", nullable = false)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Basic
    @Column(name = "updated_time", nullable = true)
    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Basic
    @Column(name = "is_deleted", nullable = false)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactoryProductBasicEntity that = (FactoryProductBasicEntity) o;

        if (factoryProductBasicId != that.factoryProductBasicId) return false;
        if (factoryId != that.factoryId) return false;
        if (productBasicId != that.productBasicId) return false;
        if (isDeleted != that.isDeleted) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (marketPrice != null ? !marketPrice.equals(that.marketPrice) : that.marketPrice != null) return false;
        if (singlePrice != null ? !singlePrice.equals(that.singlePrice) : that.singlePrice != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (updatedTime != null ? !updatedTime.equals(that.updatedTime) : that.updatedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = factoryProductBasicId;
        result = 31 * result + factoryId;
        result = 31 * result + productBasicId;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (marketPrice != null ? marketPrice.hashCode() : 0);
        result = 31 * result + (singlePrice != null ? singlePrice.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        return result;
    }
}
