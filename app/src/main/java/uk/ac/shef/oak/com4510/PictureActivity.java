package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

public class PictureActivity extends AppCompatActivity implements RetrieveInterface{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter  mAdapter;
    RetPresenter rpresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
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
        mRecyclerView = (RecyclerView) findViewById(R.id.grid_recycler_view);
        // set up the RecyclerView
        int numberOfColumns = 4;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        List<String> files= new ArrayList<String>();
        for(int i = 0;i < msgs.size();i++){
            String path = msgs.get(i).getFiles();
            path = path.substring(0,path.length() - 1);
            files.add(path);
        }
        mAdapter= new MyAdapter(files);
        mRecyclerView.setAdapter(mAdapter);
        return msgs;
    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }
}
