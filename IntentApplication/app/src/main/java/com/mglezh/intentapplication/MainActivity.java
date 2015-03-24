package com.mglezh.intentapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SEND_TEXT = 2;
    static final String TEXT_KEY = "text_key";

    private Button btnSend;
    private Button btnPhoto;
    private TextView lblToSend;
    private TextView lblToReceive;
    private ImageView Photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend =   (Button) findViewById(R.id.SendButton);
        btnPhoto =  (Button) findViewById(R.id.photoButton);

        lblToSend = (TextView) findViewById(R.id.toSendText);
        lblToReceive = (TextView) findViewById(R.id.receivedText);
        Photo = (ImageView) findViewById(R.id.imagePhoto);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(lblToSend.getText().toString());
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

    }

    private void sendData(String data){
        if (data.length() > 0) {
            Log.d("Text to send", data);
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(TEXT_KEY, data);
            startActivityForResult(intent, SEND_TEXT);
        }
        else {
            // Para mostrar mensajes temporales en la pantalla
            Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void takePhoto() {
        Log.d("Take a photo", "");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            switch (requestCode){
                case REQUEST_IMAGE_CAPTURE:
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Photo.setImageBitmap(imageBitmap);
                break;
                case SEND_TEXT:
                    lblToReceive.setText(data.getStringExtra(TEXT_KEY));
                break;
            }

        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
