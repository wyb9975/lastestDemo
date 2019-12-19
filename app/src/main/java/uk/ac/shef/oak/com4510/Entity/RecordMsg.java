/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Record msg.
 */
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


    /**
     * Instantiates a new Record msg.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public RecordMsg(float temperature, float pressure, String title, String date, String files, double lat, double lng) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.title = title;
        this.date = date;
        this.files = files;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Gets lat.
     *
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets lat.
     *
     * @param lat the lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Gets lng.
     *
     * @return the lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * Sets lng.
     *
     * @param lng the lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }


    /**
     * Gets files.
     *
     * @return the files
     */
    public String getFiles() {
        return files;
    }

    /**
     * Sets files.
     *
     * @param files the files
     */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * Sets temperature.
     *
     * @param temperature the temperature
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets pressure.
     *
     * @return the pressure
     */
    public float getPressure() {
        return pressure;
    }

    /**
     * Sets pressure.
     *
     * @param pressure the pressure
     */
    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }
}
