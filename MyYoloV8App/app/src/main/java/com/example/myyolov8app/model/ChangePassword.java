package com.example.myyolov8app.model;

public class ChangePassword {
    private boolean success;
    private String oldpas;
    private String newpass;
    private String error;

    public ChangePassword(String oldpas, String newpass) {
        this.oldpas = oldpas;
        this.newpass = newpass;
    }

    public ChangePassword(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public ChangePassword(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOldpas() {
        return oldpas;
    }

    public void setOldpas(String oldpas) {
        this.oldpas = oldpas;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ChangePassword{" +
                "success=" + success +
                ", oldpas='" + oldpas + '\'' +
                ", newpass='" + newpass + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
