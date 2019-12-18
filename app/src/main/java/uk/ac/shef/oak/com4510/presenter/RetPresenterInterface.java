package uk.ac.shef.oak.com4510.presenter;

import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;

/**
 * The interface Retreive data presenter interface.
 */
public interface RetPresenterInterface {
    /**
     * Gets data.
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
     * List data retreived.
     *
     * @param myDataset the my dataset
     */
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);
}
