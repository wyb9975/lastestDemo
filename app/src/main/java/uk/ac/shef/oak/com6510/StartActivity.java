/*
 * Copyright (c) 2019. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import uk.ac.shef.oak.com6510.com4510.R;

/**
 * The activity starting a new trip by user.User can input trip title and click start button
 * to start trip.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class StartActivity extends AppCompatActivity{
    private Button mButtonStart;
    private EditText editText;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
    }

    /**
     * Convert a number to a string with leading 0.
     *
     * @param a the number
     * @return the string with leading 0
     */
    String convertDate(int a){
        String result  =  a + "";
        if(a < 10){
            result = "0" + result;
        }
        return result;
    }
}
