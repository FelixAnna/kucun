package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "product_basic", schema = "kucun", catalog = "")
public class ProductBasicEntity {
    private int productBasicId;
    private String productName;
    private String link;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private boolean isDeleted;
    private int userId;

    @Id
    @Column(name = "product_basic_id", nullable = false)
    public int getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(int productBasicId) {
        this.productBasicId = productBasicId;
    }

    @Basic
    @Column(name = "product_name", nullable = false, length = 128)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "link", nullable = false, length = 1024)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

        ProductBasicEntity that = (ProductBasicEntity) o;

        if (productBasicId != that.productBasicId) return false;
        if (isDeleted != that.isDeleted) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (updatedTime != null ? !updatedTime.equals(that.updatedTime) : that.updatedTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productBasicId;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (updatedTime != null ? updatedTime.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        return result;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
