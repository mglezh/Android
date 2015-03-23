package com.mglezh.todolistfragments;

import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mglezh.todolistfragments.fragment.InputFragment;
import com.mglezh.todolistfragments.fragment.TodoListFragment;
import com.mglezh.todolistfragments.model.ToDo;


public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener {

    private final String TODO = "TODO";
    private InputFragment.TODOItemListener ListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ListFragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.ListFragment);
        } catch (ClassCastException ex) {
            throw new ClassCastException(this.toString() + "must implement TODOItemListener interface");
        }
    }

    public void addTodo(ToDo todo) {
        ListFragment.addTodo(todo);
    }
}
