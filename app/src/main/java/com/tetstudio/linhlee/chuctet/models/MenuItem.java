package com.tetstudio.linhlee.chuctet.models;

/**
 * Created by lequy on 1/18/2017.
 */

public class MenuItem {
    private int imgRes;
    private String name;

    public MenuItem(int imgRes, String name) {
        this.imgRes = imgRes;
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
