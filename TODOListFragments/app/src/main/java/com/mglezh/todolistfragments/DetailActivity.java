package com.mglezh.todolistfragments;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mglezh.todolistfragments.fragment.TodoListFragment;
import com.mglezh.todolistfragments.model.ToDo;

import java.text.SimpleDateFormat;


public class DetailActivity extends ActionBarActivity {

    private ToDo todo;
    private TextView lblTask;
    private TextView lblDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lblTask = (TextView) findViewById(R.id.textTask);
        lblDate = (TextView) findViewById(R.id.textDate);

        // dame el Intent q me ha abiero
        Intent detailIntent = getIntent();

        todo = (ToDo) detailIntent.getParcelableExtra(TodoListFragment.TODO_ITEM);

        populateView();

    }

    private void populateView(){
        lblTask.setText(todo.getTask());
        lblDate.setText((CharSequence) todo.getCreatedFormated());
    }

}
