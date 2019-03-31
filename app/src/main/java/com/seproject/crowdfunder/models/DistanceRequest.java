package com.seproject.crowdfunder.models;

public class DistanceRequest {

    String request_id;
    double distance;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DistanceRequest(String request_id, double distance) {
        this.request_id = request_id;
        this.distance = distance;
    }
}
