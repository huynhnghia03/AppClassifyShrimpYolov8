package com.example.myyolov8app.model;

import java.io.Serializable;
import java.util.Date;

public class Info implements Serializable {
    private int id;
    private String imageUrl;
    private String shrimpCounts;
    private int count;
    private Date timestamp;
    private String email;

    public Info(int id, String imageUrl, String shrimpCounts, int count, Date timestamp, String email) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.shrimpCounts = shrimpCounts;
        this.count = count;
        this.timestamp = timestamp;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShrimpCounts() {
        return shrimpCounts;
    }

    public void setShrimpCounts(String shrimpCounts) {
        this.shrimpCounts = shrimpCounts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", shrimpCounts='" + shrimpCounts + '\'' +
                ", count=" + count +
                ", timestamp=" + timestamp +
                ", email='" + email + '\'' +
                '}';
    }
}
