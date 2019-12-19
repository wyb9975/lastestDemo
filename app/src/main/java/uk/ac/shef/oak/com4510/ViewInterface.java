package uk.ac.shef.oak.com4510;

import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;

/**
 * The interface,provide some measure to make data interact with ui when insert data.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public interface ViewInterface {
    /**
     * Inserted feedback.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void insertedFeedback(float temperature, float pressure,String title, String date,String files,double lat,double lng);

    /**
     * do something when error occurs.
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
    public void error(float temperature, float pressure,String title, String date,String files, double lat,double lng,String errorString);

    /**
     * Return msgs list.
     *
     * @param msgs the msgs
     * @return the list
     */
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs);
}
