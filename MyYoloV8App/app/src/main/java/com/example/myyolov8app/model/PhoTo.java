package com.example.myyolov8app.model;

public class PhoTo {
    private int resourceId;

    public PhoTo(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "PhoTo{" +
                "resourceId=" + resourceId +
                '}';
    }
}
