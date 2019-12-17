package uk.ac.shef.oak.com4510;


import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;


public interface RetrieveInterface {
    public void dataRetreived(float temperature, float pressure,String title, String date,String files);
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);
    public void errorRetrievingDataDescription(float temperature, float pressure,String title, String date,String files, String errorString);
}
