package com.mglezh.todolistfragments.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mglezh.todolistfragments.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this com.mglezh.todolistfragments.fragment must implement the
 * {@link InputFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this com.mglezh.todolistfragments.fragment.
 */
public class InputFragment extends Fragment {

    public interface TODOItemListener {
        public void addTodo(String todo);
    }

    private Button btnAdd;
    private EditText todoText;

    private TODOItemListener

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            this.target = (TODOItemListener) activity;
        }
        catch (ClassCastException ex){
            throw new ClassCastException(activity.toString() + "must implement TODOItemListener ")
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this com.mglezh.todolistfragments.fragment
        View layout = inflater.inflate(R.layout.fragment_input, container, false);

        btnAdd = (Button) layout.findViewById(R.id.btnAdd);
        todoText = (EditText) layout.findViewById(R.id.todoText);

        addEventListener();

        return layout;
    }

    private void addEventListener(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoText.getText().toString();
            }
        });
    }

}
