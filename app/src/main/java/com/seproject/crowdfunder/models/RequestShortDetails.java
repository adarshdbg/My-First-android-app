package com.seproject.crowdfunder.models;

import com.seproject.crowdfunder.R;

public class RequestShortDetails
{
    private String id;
    private String title;
    private int profilePic;
    private String name;
//    private String location;
    private float rating = 5;
    private boolean bookmarked;
    private int amountRequested;
    private int backers;
    private int timeLeft;
    private int percentFunded;



    private int views;

    public RequestShortDetails() {
        this("id","Request", R.mipmap.ic_launcher_round ,"Name" ,"Location" , 5,false ,0 , 0, 0, 66, 0);
    }

    public RequestShortDetails(String id,String title , int profilePic, String name, String location, float rating, boolean bookmarked, int amountRequested, int backers, int timeLeft, int percentFunded, int views) {
        this.id = id;
        this.title = title;
        this.profilePic = profilePic;
        this.name = name;
//        this.location = location;
        this.rating = rating;
        this.bookmarked = bookmarked;
        this.amountRequested = amountRequested;
        this.backers = backers;
        this.timeLeft = timeLeft;
        this.percentFunded = percentFunded;
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public int getamountRequested() {
        return amountRequested;
    }

    public void setamountRequested(int amountRequested) {
        this.amountRequested = amountRequested;
    }

    public int getBackers() {
        return backers;
    }

    public void setBackers(int backers) {
        this.backers = backers;
    }

    public int gettimeLeft() {
        return timeLeft;
    }

    public void settimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getpercentFunded() {
        return percentFunded;
    }

    public void setpercentFunded(int percentFunded) {
        this.percentFunded = percentFunded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
