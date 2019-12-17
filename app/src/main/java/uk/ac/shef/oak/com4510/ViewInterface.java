package uk.ac.shef.oak.com4510;

public interface ViewInterface {
    public void insertedFeedback(float temperature, float pressure,String title, String date,String files);
    public void error(float temperature, float pressure,String title, String date,String files, String errorString);
}
