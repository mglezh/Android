package com.mglezh.calculator.listeners;

import android.view.View;
import android.widget.Button;


/**
 * Created by cursomovil on 20/03/15.
 */
public class NumberOnClickListener implements View.OnClickListener{

    public interface OperationListenerInterface {
        public void setOperation(String Op);

    }
/*
    private NumberListenerInterface target;

    // Constructor
    public NumberOnClickListener(NumberListenerInterface target) {
        this.target = target;
    }
*/

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
       // setNumber(btn.getText().toString());
    }
}
