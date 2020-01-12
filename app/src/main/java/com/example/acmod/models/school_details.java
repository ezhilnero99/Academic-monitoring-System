package com.example.acmod.models;

import com.google.firebase.firestore.GeoPoint;

public class school_details {
    String name,address,contact,id,logourl;
    GeoPoint location;

    public school_details(String name, String address, String contact, String id, String logourl, GeoPoint location) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.id = id;
        this.logourl = logourl;
        this.location = location;
    }

    public school_details() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
