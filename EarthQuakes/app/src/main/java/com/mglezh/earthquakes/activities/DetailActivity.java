package com.mglezh.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.database.EarthQuakesDB;
import com.mglezh.earthquakes.model.EarthQuake;


public class DetailActivity extends ActionBarActivity {

    private TextView lblMagnitude;
    private TextView lblPlace;
    private TextView lblTime;

    private EarthQuakesDB earthQuakeDB;
    private EarthQuake earthQuake;

    private final String EarthQuakes_KEY = "EarthQuakes_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        this.earthQuakeDB = new EarthQuakesDB(this);

        lblMagnitude = (TextView) findViewById(R.id.textMagnitude);
        lblPlace = (TextView) findViewById(R.id.textPlace);
        lblTime = (TextView) findViewById(R.id.textTime);

        Intent intent = getIntent();
        String id = intent.getStringExtra(EarthQuakes_KEY);

        earthQuake = earthQuakeDB.getEarthQuake(id);

        lblMagnitude.setText("Magnitude : " + Double.toString(earthQuake.getMagnitude()));
        lblPlace.setText(earthQuake.getPlace());
        lblTime.setText(earthQuake.getTime().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
