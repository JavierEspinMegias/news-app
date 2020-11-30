package com.android.myapplication;

import android.widget.DatePicker;

import java.util.Date;

public class CasualRace {
    String league, circuit, country, date, city;
    Date fullDate;

    public CasualRace(){
        this.date ="";
        this.league="";
        this.circuit ="";
        this.country="";
        this.city ="";
    }

    public CasualRace(String league, String circuit, String country, String date, String city, Date fullDate) {
        this.league = league;
        this.circuit = circuit;
        this.country = country;
        this.date = date;
        this.city = city;
        this.fullDate = fullDate;
    }

    public Date getFullDate() {
        return fullDate;
    }

    public void setFullDate(Date fullDate) {
        this.fullDate = fullDate;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
