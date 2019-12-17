package uk.ac.shef.oak.com4510.presenter;

/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */


import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.Model;
import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.RetrieveInterface;


public class RetPresenter implements RetPresenterInterface{

    RetrieveInterface userinterface;
    Model mModel;
    private final List<RecordMsg> m_list_RecordMsg = new ArrayList<>();

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as an instance of the UI interface
     * @param application
     */
    public RetPresenter(Context context, RetrieveInterface application) {
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
    public void getData(float temperature, float pressure,String title, String date,String files,double lat,double lng) {
        // send it to the model
        mModel.getData(temperature,pressure,title, date,files,lat,lng);
    }

    public void dataRetrieved(float temperature, float pressure,String title, String date,String files,double lat,double lng){
        // send it back to the UI
        userinterface.dataRetreived(temperature,pressure,title, date,files,lat,lng);
    }

    public void ListDataRetreived(ArrayList<RecordMsg> myDataset){

        userinterface.ListDataRetreived(myDataset);

    };
    public void DataRetreived(ArrayList<RecordMsg> myDataset){

        userinterface.returnMsgs(myDataset);

    };

    public void errorRetrievingTitleDescription(float temperature, float pressure,String title, String date,String files,double lat,double lng, String errorString){
        // send it back to the UI
        userinterface.errorRetrievingDataDescription(temperature,pressure,title, date,files,lat,lng, errorString);
    }

    public List<RecordMsg> GetListElements()
    {
        return m_list_RecordMsg ;
    }

}
