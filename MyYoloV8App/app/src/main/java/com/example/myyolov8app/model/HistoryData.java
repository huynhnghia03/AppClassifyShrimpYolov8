package com.example.myyolov8app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class HistoryData {
    private  Boolean auth;
    private ArrayList<String[]> datas;
    private int total;

    private int big;

    private int medium;

    private int small;
    private int sumImgs;

    public HistoryData(Boolean auth) {
        this.auth = auth;
    }

    public HistoryData(ArrayList<String[]> datas, int total, int big, int medium, int small) {
        this.datas =  datas;
        this.total = total;
        this.big = big;
        this.medium = medium;
        this.small = small;
    }

    public HistoryData(int total, int big, int medium, int small, int sumImgs) {
        this.total = total;
        this.big = big;
        this.medium = medium;
        this.small = small;
        this.sumImgs = sumImgs;
    }

    public HistoryData(Boolean auth, ArrayList<String[]> datas, int total, int big, int medium, int small) {
        this.auth = auth;
        this.datas = datas;
        this.total = total;
        this.big = big;
        this.medium = medium;
        this.small = small;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public ArrayList<String[]> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<String[]> datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getBig() {
        return big;
    }

    public void setBig(int big) {
        this.big = big;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getSumImgs() {
        return sumImgs;
    }

    public void setSumImgs(int sumImgs) {
        this.sumImgs = sumImgs;
    }


    @Override
    public String toString() {
        return "HistoryData{" +
                "auth=" + auth +
                ", datas=" + datas +
                ", total=" + total +
                ", big=" + big +
                ", medium=" + medium +
                ", small=" + small +
                ", sumImgs=" + sumImgs +
                '}';
    }
}
