package uk.ac.shef.oak.com4510.presenter;


import android.content.Context;
import uk.ac.shef.oak.com4510.Entity.Model;
import uk.ac.shef.oak.com4510.ViewInterface;

/**
 * Presenter is the class which implements PresenterInterface and provide some methods for
 * data acquisition,which calls Model's methods to operate the database and Model call
 * viewInterface's method to change UI.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class Presenter implements PresenterInterface {

    ViewInterface userinterface;
    Model mModel;

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as
     * an instance of the UI interface
     *
     * @param context     the context
     * @param application the application
     */
    public Presenter(Context context, ViewInterface application) {
        userinterface= application;
        mModel= new Model(context, this);
    }

    /**
     * this is the presenter's interface method that enables the UI to call the presenter
     * it sends the data to the model
     *
     * @param temperature
     * @param pressure
     */
    @Override
    public void insertData(float temperature, float pressure,String title, String date,String files,double lat,double lng) {
        // send it to the model
        mModel.insertData(temperature,pressure,title, date,files,lat,lng);
    }


    /**
     * it receives confirmation of correct insertion of data. It sends them back to the UI
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void dataInserted(float temperature, float pressure,String title, String date,String files,double lat,double lng){
        // send it back to the UI
        userinterface.insertedFeedback(temperature,pressure,title, date,files,lat,lng);
    }

    /**
     * it receives confirmation of error insertion of data. It sends them back to the UI
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
    public void errorInsertingData(float temperature, float pressure,String title, String date,String files,double lat,double lng,String errorString){
        // send it back to the UI
        userinterface.error(temperature,pressure,title, date,files, lat,lng,errorString);
    }


    /**
     * Gets userinterface.
     *
     * @return the userinterface
     */
    public ViewInterface getUserinterface() {
        return userinterface;
    }
}

