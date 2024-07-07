package com.example.myyolov8app.model;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {
    public List<Users> dataUsers;
    private int totalHistory;
    private  int totalUsers;

    public AllUsers(List<Users> dataUsers, int totalHistory, int totalUsers) {
        this.dataUsers = dataUsers;
        this.totalHistory = totalHistory;
        this.totalUsers = totalUsers;
    }

    public List<Users> getDataUsers() {
        return dataUsers;
    }

    public void setDataUsers(List<Users> dataUsers) {
        this.dataUsers = dataUsers;
    }

    public int getTotalHistory() {
        return totalHistory;
    }

    public void setTotalHistory(int totalHistory) {
        this.totalHistory = totalHistory;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    @Override
    public String toString() {
        return "AllUsers{" +
                "dataUsers=" + dataUsers +
                ", totalHistory=" + totalHistory +
                ", totalUsers=" + totalUsers +
                '}';
    }
}
