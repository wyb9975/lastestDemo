package uk.ac.shef.oak.com6510.presenter;

/**
 * The interface Presenter interface.
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public interface PresenterInterface {
    /**
     * Inserts data to dataBase.
     *
     * @param temperature the temperature
     * @param pressure    the pressure
     * @param title       the title
     * @param date        the date
     * @param files       the files
     * @param lat         the lat
     * @param lng         the lng
     */
    public void insertData(float temperature, float pressure,String title, String date,String files,double lat,double lng);
}

