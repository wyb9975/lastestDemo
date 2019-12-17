/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;
import uk.ac.shef.oak.com4510.presenter.Presenter;
import uk.ac.shef.oak.com4510.presenter.RetPresenter;

public class StartActivity extends AppCompatActivity implements ViewInterface,RetrieveInterface{
    private Button mButtonStart;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Presenter mPresenter= new Presenter(getApplicationContext(),this);
        setContentView(R.layout.activity_start);
        mButtonStart = (Button) findViewById(R.id.button_start);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        mPresenter.insertData((float)0.1,(float)0.1,"1000","1000","1000");
        final RetPresenter mPresenter2= new RetPresenter(getApplicationContext(),this);
        float a = 0;
        float b = 0;
        String title = "";
        String date = "";
        String files = "";
        mPresenter2.getData(a, b,title,date,files);
        Log.d("opopopop",a + " " + b + " " + title + " " + date + " " + files);
    }

    @Override
    public void insertedFeedback(float temperature, float pressure, String title, String date, String files) {

    }

    @Override
    public void error(float temperature, float pressure, String title, String date, String files, String errorString) {

    }

    @Override
    public void dataRetreived(float temperature, float pressure, String title, String date, String files) {

    }

    @Override
    public void ListDataRetreived(ArrayList<RecordMsg> myDataset) {

    }

    @Override
    public void errorRetrievingDataDescription(float temperature, float pressure, String title, String date, String files, String errorString) {

    }
}
