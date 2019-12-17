/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.Presenter;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

public class StartActivity extends AppCompatActivity implements ViewInterface,RetrieveInterface{
    private Button mButtonStart;
    private EditText editText;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Presenter mPresenter= new Presenter(getApplicationContext(),this);
        setContentView(R.layout.activity_start);
        mButtonStart = (Button) findViewById(R.id.button_start);
        editText = (EditText)findViewById(R.id.editTitle);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MapsActivity.class);
                Calendar c = Calendar.getInstance();
                String year = convertDate(c.get(Calendar.YEAR));
                String month = convertDate(c.get(Calendar.MONTH));
                String date = convertDate(c.get(Calendar.DATE));
                String hour = convertDate(c.get(Calendar.HOUR_OF_DAY));
                String minute = convertDate(c.get(Calendar.MINUTE));
                String second = convertDate(c.get(Calendar.SECOND));
                String title = editText.getText().toString();
                String currDate = year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second;
                intent.putExtra("currDate", currDate);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        /*mPresenter.insertData((float)0.1,(float)0.1,"1000","1000","1000");
        final RetPresenter mPresenter2= new RetPresenter(getApplicationContext(),this);
        float a = 0;
        float b = 0;
        String title = "";
        String date = "";
        String files = "";
        mPresenter2.getData(a, b,title,date,files);
        Log.d("opopopop",a + " " + b + " " + title + " " + date + " " + files);*/
    }

    @Override
    public void dataRetreived(float temperature, float pressure, String title, String date, String files, double lat, double lng) {

    }

    @Override
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset) {

    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }

    @Override
    public void insertedFeedback(float temperature, float pressure, String title, String date, String files, double lat, double lng) {

    }

    @Override
    public void error(float temperature, float pressure, String title, String date, String files, double lat, double lng, String errorString) {

    }

    @Override
    public List<RecordMsg> returnMsgs(List<RecordMsg> msgs) {
        return null;
    }

    String convertDate(int a){
        String result  =  a + "";
        if(a < 10){
            result = "0" + result;
        }
        return result;
    }
}
