package uk.ac.shef.oak.com6510;


import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.Entity.RecordMsg;


/**
 * The interface Retrieve interface,provide some measure to make data interact with ui when retrieve data.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public interface RetrieveInterface {
    /**
     * Data retreived.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void dataRetreived(float temperature, float pressure,String title, String date,String files,double lat,double lng);

    /**
     * List data retreived.
     *
     * @param myDataset the my dataset
     */
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);

    /**
     * Return msgs list.
     *
     * @param msgs the msgs
     * @return the list
     */
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs);

    /**
     * Error retrieving data.
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
    public void errorRetrievingDataDescription(float temperature, float pressure,String title, String date,String files, double lat,double lng,String errorString);
}
