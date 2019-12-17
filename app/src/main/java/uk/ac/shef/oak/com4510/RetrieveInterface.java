package uk.ac.shef.oak.com4510;


import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;


public interface RetrieveInterface {
    public void dataRetreived(float temperature, float pressure,String title, String date,String files,double lat,double lng);
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs);
    public void errorRetrievingDataDescription(float temperature, float pressure,String title, String date,String files, double lat,double lng,String errorString);
}
