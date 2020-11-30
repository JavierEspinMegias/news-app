package com.android.myapplication;

import java.io.Serializable;

public class CasualMeet implements Serializable {
    String id, main_user, day, hour_start, hour_end, ride_type, latitude_start, longitude_start, latitude_end, longitude_end, description;
    int duration, distance;

    public CasualMeet() {
        this.id = this.main_user = this.day = this.hour_start = this.ride_type = this.latitude_start = this.longitude_start = this.latitude_end = this.longitude_end = this.description ="";
        this.duration = this.distance = -1;
    }

    public CasualMeet(String id, String main_user, String day, String hour_start, String hour_end, String ride_type, String latitude_start, String longitude_start, String latitude_end, String longitude_end, String description, int duration, int distance) {
        this.id = id;
        this.main_user = main_user;
        this.day = day;
        this.hour_start = hour_start;
        this.hour_end = hour_end;
        this.ride_type = ride_type;
        this.latitude_start = latitude_start;
        this.longitude_start = longitude_start;
        this.latitude_end = latitude_end;
        this.longitude_end = longitude_end;
        this.description = description;
        this.duration = duration;
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain_user() {
        return main_user;
    }

    public void setMain_user(String main_user) {
        this.main_user = main_user;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour_start() {
        return hour_start;
    }

    public void setHour_start(String hour_start) {
        this.hour_start = hour_start;
    }

    public String getHour_end() {
        return hour_end;
    }

    public void setHour_end(String hour_end) {
        this.hour_end = hour_end;
    }

    public String getRide_type() {
        return ride_type;
    }

    public void setRide_type(String ride_type) {
        this.ride_type = ride_type;
    }

    public String getLatitude_start() {
        return latitude_start;
    }

    public void setLatitude_start(String latitude_start) {
        this.latitude_start = latitude_start;
    }

    public String getLongitude_start() {
        return longitude_start;
    }

    public void setLongitude_start(String longitude_start) {
        this.longitude_start = longitude_start;
    }

    public String getLatitude_end() {
        return latitude_end;
    }

    public void setLatitude_end(String latitude_end) {
        this.latitude_end = latitude_end;
    }

    public String getLongitude_end() {
        return longitude_end;
    }

    public void setLongitude_end(String longitude_end) {
        this.longitude_end = longitude_end;
    }
}
