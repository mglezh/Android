package com.mglezh.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;


public class MainActivity extends ActionBarActivity {

    public Button btnPunto,btnMasMenos;
    private Button btnMas,btnMenos,btnPor,btnDiv,btnIgual;
    private EditText Number;
    private float Number1, Number2, Result;
    private Character Op;
    private int index;

    private String[] btnNumbers = {"0","1","2","3","4","5","6","7","8","9","."};
    private Character[] OpChars = {'+','-','*','/'};


    public MainActivity() {
    }


    //   public MainActivity() {
 //   }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Number = (EditText) findViewById(R.id.editTextNumber);
        btnPunto = (Button) findViewById(R.id.btnPunto);
        btnIgual = (Button) findViewById(R.id.btnIgual);

        String btnId;
        Button btn;

        for (index = 0; index <= 9; index++) {
            btnId = "btn".concat(btnNumbers[index]);
            btn  = (Button) findViewById(getResources().getIdentifier(btnId, "id", getPackageName()));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectNumber(btnNumbers[index]);
                }
            });
        }

        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNumber(".");
             }
        });

        String[] Operators = {"Mas","Menos","Por","Div"};

        for (index = 0; index <= 3; index++) {
            btnId = "btn".concat(Operators[index]);
            btn = (Button) findViewById(getResources().getIdentifier(btnId, "id", getPackageName()));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOp(OpChars[index]);
                }
            });
        }

        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEqual();
            }
        });

    }

    private void selectNumber(String Digit){
         Number.setText(Number.getText() + Digit);
    }

    private void selectOp(Character Operator){
        Number1 = Float.parseFloat(Number.getText().toString());
        Number.setText("");
        Op = Operator;
    }

    private void selectEqual(){
        Number2 = Float.parseFloat(Number.getText().toString());
        switch (Op){
            case '+' : Result = Number1 + Number2;
                break;
            case '-' : Result = Number1 - Number2;
                break;
            case '*' : Result = Number1 * Number2;
                break;
            case '/' : Result = Number1 / Number2;
                break;
        }
        Number.setText(Float.toString(Result).toString());
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
