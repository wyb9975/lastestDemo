package uk.ac.shef.oak.com4510;

import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;

public interface ViewInterface {
    public void insertedFeedback(float temperature, float pressure,String title, String date,String files,double lat,double lng);
    public void error(float temperature, float pressure,String title, String date,String files, double lat,double lng,String errorString);
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs);
}
