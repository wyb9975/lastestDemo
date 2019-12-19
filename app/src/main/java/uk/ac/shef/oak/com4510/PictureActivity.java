package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

/**
 * The activity used to browse previews of photos.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class PictureActivity extends AppCompatActivity implements RetrieveInterface{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter  mAdapter;
    private TextView textView;
    /**
     * The Rpresenter.
     */
    RetPresenter rpresenter;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        textView = findViewById(R.id.textView);
        rpresenter= new RetPresenter(getApplicationContext(),this);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        String t = intent.getStringExtra("title");
        if(!t.equals("")){
            textView.setText(t);
        }
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
        int numberOfColumns = 2;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        List<String> files= new ArrayList<String>();
        for(int i = 0;i < msgs.size();i++){
            Log.d("testDate",msgs.get(i).getDate());
            Log.d("testDate2",date);
            if(!date.equals("") && !msgs.get(i).getDate().equals(date)){
                continue;
            }
            String path = msgs.get(i).getFiles();
            path = path.substring(0,path.length() - 1);
            StringTokenizer st = new StringTokenizer(path, ";");
            while(st.hasMoreElements()){
                String temp = st.nextToken();
                if(temp != ""){
                    files.add(temp);
                }
            }
        }
        mAdapter= new MyAdapter(files);
        mRecyclerView.setAdapter(mAdapter);
        return msgs;
    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }
}
