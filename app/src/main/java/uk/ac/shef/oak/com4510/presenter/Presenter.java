package uk.ac.shef.oak.com4510.presenter;

/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */


import android.content.Context;
import uk.ac.shef.oak.com4510.Entity.Model;
import uk.ac.shef.oak.com4510.ViewInterface;

public class Presenter implements PresenterInterface {
    ViewInterface userinterface;
    Model mModel;

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as an instance of the UI interface
     * @param application
     */
    public Presenter(Context context, ViewInterface application) {
        userinterface= application;
        mModel= new Model(context, this);
    }

    /**
     * this is the presenter's interface method that enables the UI to call the presenter
     * it sends the data to the model
     * @param temperature
     * @param pressure
     */
    @Override
    public void insertData(float temperature, float pressure,String title, String date,String files) {
        // send it to the model
        mModel.insertData(temperature,pressure,title, date,files);
    }


    /**
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
     * @param temperature
     * @param pressure
     */
    public void dataInserted(float temperature, float pressure,String title, String date,String files){
        // send it back to the UI
        userinterface.insertedFeedback(temperature,pressure,title, date,files);
    }

    /**
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
     * @param temperature
     * @param pressure
     * @param errorString
     */
    public void errorInsertingData(float temperature, float pressure,String title, String date,String files,String errorString){
        // send it back to the UI
        userinterface.error(temperature,pressure,title, date,files, errorString);
    }


    public ViewInterface getUserinterface() {
        return userinterface;
    }
}

