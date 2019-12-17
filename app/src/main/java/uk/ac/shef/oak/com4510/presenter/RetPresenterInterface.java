package uk.ac.shef.oak.com4510.presenter;

import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;

public interface RetPresenterInterface {
    public void getData(float temperature, float pressure,String title, String date,String files,double lat,double lng);
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset);
}
