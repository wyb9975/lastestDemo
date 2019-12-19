package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.shef.oak.com4510.com4510.R;

/**
 * The MainActivity is the program entry activity.There are three buttons on the interface.
 * one button is clicked to start a new trip,one button is clicked to browse photos by date,and
 * the last button is clicked to browse photos by path.
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class MainActivity extends AppCompatActivity {

    Button tripButton;
    Button browseButton;
    Button pathButton;
    @Override
    /**
     *do some necessary initialization work when this activity starts
     *@param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tripButton = (Button) findViewById(R.id.button_Trip);
        browseButton = (Button)findViewById(R.id.button_record);
        pathButton = (Button)findViewById(R.id.button_path);
        tripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                intent.putExtra("date", "");
                intent.putExtra("title", "");
                startActivity(intent);
            }
        });
        pathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PathActivity.class);
                startActivity(intent);
            }
        });
    }
}
