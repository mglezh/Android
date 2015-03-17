package com.mglezh.configchange;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityA extends ActionBarActivity {

    private final String DATA = "data";

    private String op = null;
    private Double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Log.d("CHANGE", "ActivityA onCreate()");

        if (saveInstanceState != null) {
            String data = savedInstanceState.getString(DATA);
            this.op = savedInstanceState.getString("op");
            this.result = savedInstanceState.getDouble("result");

            Log.d("CHANGE", "ActivityA onCreate() Recupera datos" + data);
        }

        Button btnOpenB = (Button) findViewById(R.id.btnOpenB);
        Button btnClose = (Button) findViewById(R.id.btnClose);

        btnOpenB.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA onClick()");
                Intent openB = new Intent(ActivityA.this, ActivityB.class);
                startActivity(openB);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA close");
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CHANGE", "ActivityA onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        this.op = savedInstanceState.getString("op");
        this.result = savedInstanceState.getDouble("result");

        String data = savedInstanceState.getString(DATA);
        Log.d("CHANGE", "ActivityA onRestoreInstanceState() Recupera datos" + data);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CHANGE", "ActivityA onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHANGE", "ActivityA onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHANGE", "ActivityA onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHANGE", "ActivityA onStop()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(DATA, "datos");
        outState.putString(this.op, "op");
        outState.putDouble(this.result, "result");

        super.onSaveInstanceState(outState);

        Log.d("CHANGE", "ActivityA onSaveInstanceState()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHANGE", "ActivityA onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
