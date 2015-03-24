package com.mglezh.intentapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends ActionBarActivity {

    private Button btnSave;
    private Button btnClose;
    private TextView lblToSend;
    private TextView lblToReceive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnSave = (Button) findViewById(R.id.saveButton);
        btnClose = (Button) findViewById(R.id.closeButton);
        lblToSend = (TextView) findViewById(R.id.toSendText);
        lblToReceive = (TextView) findViewById(R.id.receivedText);

        //Bundle bundle = getIntent().getExtras();
        //lblToReceive.setText(bundle.getString(TEXT_KEY));
        lblToReceive.setText(getIntent().getStringExtra(MainActivity.TEXT_KEY));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(lblToSend.getText().toString());
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });


    }

    private void saveData(String data){
        if (data.length() > 0) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.TEXT_KEY, data);

            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            // Para mostrar mensajes temporales en la pantalla
            Toast toast = Toast.makeText(SecondActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void closeActivity(){
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
