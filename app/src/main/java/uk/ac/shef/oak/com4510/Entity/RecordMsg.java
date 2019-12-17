/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;

@Entity
public class RecordMsg {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "temperature")
    private float temperature;
    @ColumnInfo(name = "pressure")
    private float pressure;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "files")
    private String files;
    @ColumnInfo(name = "lat")
    private double lat;
    @ColumnInfo(name = "lng")
    private double lng;
    public RecordMsg(float temperature, float pressure, String title, String date, String files, double lat, double lng) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.title = title;
        this.date = date;
        this.files = files;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }



    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    /*public RecordMsg(float temperature, float pressure, String title, String date, String files) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.title = title;
        this.date = date;
        this.files = files;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
