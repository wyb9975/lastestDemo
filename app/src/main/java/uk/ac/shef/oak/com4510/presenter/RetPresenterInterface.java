package uk.ac.shef.oak.com4510.presenter;

import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;

/**
 * The interface Retreive data presenter interface.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public interface RetPresenterInterface {
    /**
     * Gets data from dataBase.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void getData(float temperature, float pressure,String title, String date,String files,double lat,double lng);

    /**
     * Gets data list from dataBase.
     *
     * @param myDataset the my dataset
     */
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);
}
