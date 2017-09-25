package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "size", schema = "kucun", catalog = "")
public class SizeEntity {
    private int sizeId;
    private String name;
    private int size;
    private Integer maxHeight;
    private Integer minHeight;
    private Timestamp createdTime;

    @Id
    @Column(name = "size_id", nullable = false)
    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "size", nullable = false)
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Basic
    @Column(name = "max_height", nullable = true)
    public Integer getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Integer maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Basic
    @Column(name = "min_height", nullable = true)
    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    @Basic
    @Column(name = "created_time", nullable = false)
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SizeEntity that = (SizeEntity) o;

        if (sizeId != that.sizeId) return false;
        if (size != that.size) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (maxHeight != null ? !maxHeight.equals(that.maxHeight) : that.maxHeight != null) return false;
        if (minHeight != null ? !minHeight.equals(that.minHeight) : that.minHeight != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sizeId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + size;
        result = 31 * result + (maxHeight != null ? maxHeight.hashCode() : 0);
        result = 31 * result + (minHeight != null ? minHeight.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        return result;
    }
}
