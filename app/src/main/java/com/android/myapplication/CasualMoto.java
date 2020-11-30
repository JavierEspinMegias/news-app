package com.android.myapplication;

public class CasualMoto {
    String id, model, cv, type, name, owner;

    public CasualMoto(){
        this.id="";
        this.name="";
        this.model="";
        this.cv="";
        this.type="";
        this.owner="";
    }

    public CasualMoto(String id, String model, String cv, String type, String name, String owner) {
        this.id = id;
        this.model = model;
        this.cv = cv;
        this.type = type;
        this.name = name;
        this.owner=owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
