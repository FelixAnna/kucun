package com.xiangyong.manager.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "color", schema = "kucun", catalog = "")
public class ColorEntity {
    private int colorId;
    private String name;
    private String rgb;
    private Timestamp createdTime;

    @Id
    @Column(name = "color_id", nullable = false)
    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
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
    @Column(name = "rgb", nullable = false, length = 16)
    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
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

        ColorEntity that = (ColorEntity) o;

        if (colorId != that.colorId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (rgb != null ? !rgb.equals(that.rgb) : that.rgb != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = colorId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rgb != null ? rgb.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        return result;
    }
}
