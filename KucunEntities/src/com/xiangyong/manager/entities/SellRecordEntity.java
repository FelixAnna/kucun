package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "sell_record", schema = "kucun", catalog = "")
public class SellRecordEntity {
    private int sellRecordId;
    private int productDetailId;
    private int totalNumber;
    private BigDecimal totalPrice;
    private int status;
    private String remark;
    private Timestamp createdTime;
    private Timestamp finishedTime;
    private Boolean isDeleted;

    @Id
    @Column(name = "sell_record_id", nullable = false)
    public int getSellRecordId() {
        return sellRecordId;
    }

    public void setSellRecordId(int sellRecordId) {
        this.sellRecordId = sellRecordId;
    }

    @Basic
    @Column(name = "product_detail_id", nullable = false)
    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    @Basic
    @Column(name = "total_number", nullable = false)
    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Basic
    @Column(name = "total_price", nullable = false, precision = 2)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "remark", nullable = false, length = 256)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "finished_time", nullable = true)
    public Timestamp getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Timestamp finishedTime) {
        this.finishedTime = finishedTime;
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

        SellRecordEntity that = (SellRecordEntity) o;

        if (sellRecordId != that.sellRecordId) return false;
        if (productDetailId != that.productDetailId) return false;
        if (totalNumber != that.totalNumber) return false;
        if (status != that.status) return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (finishedTime != null ? !finishedTime.equals(that.finishedTime) : that.finishedTime != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sellRecordId;
        result = 31 * result + productDetailId;
        result = 31 * result + totalNumber;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (finishedTime != null ? finishedTime.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }
}
