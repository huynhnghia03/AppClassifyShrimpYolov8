package com.example.myyolov8app.model;

import java.util.ArrayList;
import java.util.List;

public class AdminUser {
    private Users dataUser;
    private HistoryData  datas;

    private ArrayList<String[]> dataHistories;

    public AdminUser(Users dataUser, HistoryData datas) {
        this.dataUser = dataUser;
        this.datas = datas;
    }

    public AdminUser(Users dataUser, HistoryData datas, ArrayList<String[]> dataHistories) {
        this.dataUser = dataUser;
        this.datas = datas;
        this.dataHistories = dataHistories;
    }

    public Users getDataUser() {
        return dataUser;
    }

    public void setDataUser(Users dataUser) {
        this.dataUser = dataUser;
    }

    public HistoryData getDatas() {
        return datas;
    }

    public ArrayList<String[]> getDataHistories() {
        return dataHistories;
    }

    public void setDataHistories(ArrayList<String[]> dataHistories) {
        this.dataHistories = dataHistories;
    }

    public void setDatas(HistoryData datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "dataUser=" + dataUser +
                ", datas=" + datas +
                ", dataHistories=" + dataHistories +
                '}';
    }
}
