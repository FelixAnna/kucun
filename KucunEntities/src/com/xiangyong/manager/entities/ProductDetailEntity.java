package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product_detail", schema = "kucun", catalog = "")
public class ProductDetailEntity {
    private int productDetailId;
    private int productBasicId;
    private int colorId;
    private int sizeId;
    private int total;
    private int sold;
    private BigDecimal price;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private Boolean isDeleted;

    @Id
    @Column(name = "product_detail_id", nullable = false)
    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
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
    @Column(name = "color_id", nullable = false)
    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    @Basic
    @Column(name = "size_id", nullable = false)
    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    @Basic
    @Column(name = "total", nullable = false)
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Basic
    @Column(name = "sold", nullable = false)
    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
    @Column(name = "is_deleted", nullable = true)
    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDetailEntity that = (ProductDetailEntity) o;

        if (productDetailId != that.productDetailId) return false;
        if (productBasicId != that.productBasicId) return false;
        if (colorId != that.colorId) return false;
        if (sizeId != that.sizeId) return false;
        if (total != that.total) return false;
        if (sold != that.sold) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (updatedTime != null ? !updatedTime.equals(that.updatedTime) : that.updatedTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productDetailId;
        result = 31 * result + productBasicId;
        result = 31 * result + colorId;
        result = 31 * result + sizeId;
        result = 31 * result + total;
        result = 31 * result + sold;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }
}
