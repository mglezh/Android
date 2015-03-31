package com.mglezh.earthquakes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mglezh.earthquakes.R;
import com.mglezh.earthquakes.model.EarthQuake;

import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

    private int resource;

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;
        if (convertView == null) {
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);

        } else {
            layout = (LinearLayout) convertView;
        }

        EarthQuake item = getItem(position);

        TextView lblMagnitude = (TextView) layout.findViewById(R.id.textMagnitude);
        TextView lblPlace = (TextView) layout.findViewById(R.id.textPlace);
        TextView lblTime = (TextView) layout.findViewById(R.id.textTime);

        double mag = item.getMagnitude();

        int n = (int) (mag * 15);
        int color = Color.rgb((255*n)/100,((255*100)-n)/100,0);

        lblMagnitude.setText(Double.toString(mag));
        lblMagnitude.setBackgroundColor(color);
        lblMagnitude.invalidate();
        lblPlace.setText(item.getPlace());
        lblTime.setText(item.getTime().toString());

        return layout;
    }

}
