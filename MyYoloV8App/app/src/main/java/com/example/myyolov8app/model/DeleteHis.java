package com.example.myyolov8app.model;

public class DeleteHis {
    private boolean success;
    private String id;
    private String error;

    public DeleteHis(boolean success, String err) {
        this.success = success;
        this.error = err;
    }

    public DeleteHis(String id) {
        this.id = id;
    }

    public DeleteHis(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteHis{" +
                "success=" + success +
                ", id='" + id + '\'' +
                ", err='" + error + '\'' +
                '}';
    }
}
