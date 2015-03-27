package com.mglezh.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.fragments.EathQuakeListFragment;
import com.mglezh.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthquakesTask.AddEarthQuakeInterface {

    static final int PREFS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downLoadEartQuakes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent prefIntent = new Intent(this, SettingActivity.class);
            startActivityForResult(prefIntent, PREFS_ACTIVITY);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void notifyTotal(Integer Total) {
        String msg = getString(R.string.num_earth_Quakes, Total);
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Para que se actualiza la lista cuando se vuelva a esta activity ya creada
        downLoadEartQuakes();
    }

    private void downLoadEartQuakes(){
        DownloadEarthquakesTask task = new DownloadEarthquakesTask(this, this);
        task.execute(getString(R.string.earthquakes_url));
    }

}
