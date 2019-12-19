package uk.ac.shef.oak.com4510.presenter;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.Model;
import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.RetrieveInterface;


/**
 * RetPresenter is the class which implements RetPresenterInterface and provide some methods for
 * data acquisition,which calls Model's methods to operate the database and Model call viewInterface's
 * method to change UI.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class RetPresenter implements RetPresenterInterface{

    RetrieveInterface userinterface;
    Model mModel;
    private final List<RecordMsg> m_list_RecordMsg = new ArrayList<>();

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as an instance of the UI interface
     *
     * @param context     the context
     * @param application the application
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

    /**
     * it receives confirmation of correct retrieve of data. It sends them back to the UI.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void dataRetrieved(float temperature, float pressure,String title, String date,String files,double lat,double lng){
        // send it back to the UI
        userinterface.dataRetreived(temperature,pressure,title, date,files,lat,lng);
    }

    /**
     * it receives confirmation of correct retrieve of data list. It sends them back to the UI.
     *
     * @param myDataset the my dataset
     */
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset){

        userinterface.ListDataRetreived(myDataset);

    };

    /**
     * it receives confirmation of correct retrieve of data. It sends them back to the UI.
     *
     * @param myDataset the my dataset
     */
    public void DataRetreived(ArrayList<RecordMsg> myDataset){

        userinterface.returnMsgs(myDataset);

    };

    /**
     * it receives confirmation of error retrieve of data. It sends them back to the UI.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     * @param errorString the error string
     */
    public void errorRetrieving(float temperature, float pressure,String title, String date,String files,double lat,double lng, String errorString){
        // send it back to the UI
        userinterface.errorRetrievingDataDescription(temperature,pressure,title, date,files,lat,lng, errorString);
    }

    /**
     * Get list elements list.
     *
     * @return the list
     */
    public List<RecordMsg> GetListElements()
    {
        return m_list_RecordMsg ;
    }

}
