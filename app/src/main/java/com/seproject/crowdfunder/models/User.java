package com.seproject.crowdfunder.models;

import java.util.ArrayList;
import java.util.Iterator;

public class User {

    String uid;
    int can_notify;
    int is_account_active;
    int is_deactivated;
    int is_donor;
    int is_online;
    int is_requestor;
    String name;
    int no_of_bookmarks;
    int no_of_donations;
    int no_of_requests;
    float rating;
    int wallet;



    public int getCan_notify() {
        return can_notify;
    }

    public void setCan_notify(int can_notify) {
        this.can_notify = can_notify;
    }

    public int getIs_account_active() {
        return is_account_active;
    }

    public void setIs_account_active(int is_account_active) {
        this.is_account_active = is_account_active;
    }

    public int getIs_deactivated() {
        return is_deactivated;
    }

    public void setIs_deactivated(int is_deactivated) {
        this.is_deactivated = is_deactivated;
    }

    public int getIs_donor() {
        return is_donor;
    }

    public void setIs_donor(int is_donor) {
        this.is_donor = is_donor;
    }

    public int getIs_online() {
        return is_online;
    }

    public void setIs_online(int is_online) {
        this.is_online = is_online;
    }

    public int getIs_requestor() {
        return is_requestor;
    }

    public void setIs_requestor(int is_requestor) {
        this.is_requestor = is_requestor;
    }

    public int getNo_of_bookmarks() {
        return no_of_bookmarks;
    }

    public void setNo_of_bookmarks(int no_of_bookmarks) {
        this.no_of_bookmarks = no_of_bookmarks;
    }

    public int getNo_of_donations() {
        return no_of_donations;
    }

    public void setNo_of_donations(int no_of_donations) {
        this.no_of_donations = no_of_donations;
    }

    public int getNo_of_requests() {
        return no_of_requests;
    }

    public void setNo_of_requests(int no_of_requests) {
        this.no_of_requests = no_of_requests;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
