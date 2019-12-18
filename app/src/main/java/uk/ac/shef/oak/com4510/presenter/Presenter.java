package uk.ac.shef.oak.com4510.presenter;


import android.content.Context;
import uk.ac.shef.oak.com4510.Entity.Model;
import uk.ac.shef.oak.com4510.ViewInterface;

/**
 * The type Presenter.
 */
public class Presenter implements PresenterInterface {
    /**
     * The Userinterface.
     */
    ViewInterface userinterface;
    /**
     * The M model.
     */
    Model mModel;

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as an instance of the UI interface
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
     * @param temperature
     * @param pressure
     */
    @Override
    public void insertData(float temperature, float pressure,String title, String date,String files,double lat,double lng) {
        // send it to the model
        mModel.insertData(temperature,pressure,title, date,files,lat,lng);
    }


    /**
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
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
     * it receives confirmation of correct insertion of title and description. It sends them back to the UI
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

