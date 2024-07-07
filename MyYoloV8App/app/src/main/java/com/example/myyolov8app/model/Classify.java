package com.example.myyolov8app.model;

public class Classify {
    public String Filename;
    public HistoryData Info;
    public String msg;
    public Boolean video;

    public  String date;
    public Boolean success;

    public Classify(String filename, HistoryData info, String msg, Boolean video, Boolean success) {
        this.Filename = filename;
        this.Info = info;
        this.msg = msg;
        this.video = video;
        this.success = success;
    }

    public Classify(String filename, HistoryData info, String msg, Boolean video, String date, Boolean success) {
        Filename = filename;
        this.Info = info;
        this.msg = msg;
        this.video = video;
        this.date = date;
        this.success = success;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        this.Filename = filename;
    }

    public HistoryData getInfo() {
        return Info;
    }

    public void setInfo(HistoryData info) {
        Info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Classify{" +
                "Filename='" + Filename + '\'' +
                ", Info=" + Info +
                ", msg='" + msg + '\'' +
                ", video=" + video +
                ", date='" + date + '\'' +
                ", success=" + success +
                '}';
    }
}
