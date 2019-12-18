package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

/**
 * The activity used to browse picture via a list .
 */
public class PathActivity extends AppCompatActivity implements RetrieveInterface{
    private RecyclerView mRecyclerView;
    private ListAdapter  mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * The Rpresenter.
     */
    RetPresenter rpresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        rpresenter= new RetPresenter(getApplicationContext(),this);
        float a = 0;
        float b = 0;
        String title = "";
        String date = "";
        String files = "";
        double lat = 0;
        double lng = 0;
        rpresenter.getData(a, b,title,date,files,lat,lng);

    }

    @Override
    public void dataRetreived(float temperature, float pressure, String title, String date, String files, double lat, double lng) {

    }

    @Override
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset) {

    }

    @Override
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs) {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_list);
        // set up the RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<ArrayList<RecordMsg>> items = generateList(msgs);
        for(int i = 0;i < items.size();i++){
            for(int j = 0;j < items.get(i).size();j++){
                Log.d("6546" + i,items.get(i).get(j).getTitle());
            }
        }
        mAdapter= new ListAdapter(items);
        mRecyclerView.setAdapter(mAdapter);
        return msgs;
    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }

    /**
     * Generate list array list.
     *
     * @param msgs the msgs
     * @return the array list
     */
    ArrayList<ArrayList<RecordMsg>> generateList(List<RecordMsg> msgs){
        ArrayList<RecordMsg> list = new ArrayList<RecordMsg>();
        ArrayList<ArrayList<RecordMsg>> result = new ArrayList<ArrayList<RecordMsg>>();
        for(int i = 0;i < msgs.size();i++){
            Log.d("1213",msgs.get(i).getTitle());
        }
        for(RecordMsg msg:msgs){
            Log.d("7879",msg.getTitle() + " test");
            if(list.size() == 0||(list.get(0).getDate().equals(msg.getDate()))){
                list.add(msg);
            }else{
                ArrayList<RecordMsg> temp = new ArrayList<RecordMsg>();
                temp=(ArrayList<RecordMsg>) list.clone();
                result.add(temp);
                for(int i = 0;i < temp.size();i++){
                    Log.d("7879",temp.get(i).getTitle());
                }
                list = new ArrayList<RecordMsg>();
                list.add(msg);
            }
        }
        if(list.size() != 0){
            ArrayList<RecordMsg> temp = new ArrayList<RecordMsg>();
            temp=(ArrayList<RecordMsg>) list.clone();
            result.add(temp);
            for(int i = 0;i < temp.size();i++){
                Log.d("7879",temp.get(i).getTitle());
            }
        }
        for(int i = 0;i < result.size();i++){
            for(int j = 0;j < result.get(i).size();j++){
                Log.d("6545" + i,result.get(i).get(j).getTitle());
            }
        }
        Log.d("opop",result.size() +"");
        return result;

    }
}

