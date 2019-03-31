package com.seproject.crowdfunder.models;

import android.location.Location;

public class Request {

    private String title;
    private String start_date;
    private String type;
    //private String user_id;
    private int request_id;
    private int amount_funded;
    private float lat;
    private float lon;
    private String desc;
    private float amount_required;
    private int backers;
    private String uid;
    private int days_left;
    private int views;
    private String user_name;

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(String user_id) {
//        this.user_id = user_id;
//    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getAmount_funded() {
        return amount_funded;
    }

    public void setAmount_funded(int amount_funded) {
        this.amount_funded = amount_funded;
    }

    public float getAmount_required() {
        return amount_required;
    }

    public void setAmount_required(float amount_required) {
        this.amount_required = amount_required;
    }

    public int getBackers() {
        return backers;
    }

    public void setBackers(int backers) {
        this.backers = backers;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getDays_left() {
        return days_left;
    }

    public void setDays_left(int days_left) {
        this.days_left = days_left;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
