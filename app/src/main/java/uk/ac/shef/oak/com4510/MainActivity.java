package uk.ac.shef.oak.com4510;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.shef.oak.com4510.com4510.R;

/**
 * Program entry
 * There are three buttons on the interface
 *
 * @author Yuzhou Zhang
 * @version V1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Trip button.
     */
    Button tripButton;
    /**
     * The Browse button.
     */
    Button browseButton;
    /**
     * The Path button.
     */
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
