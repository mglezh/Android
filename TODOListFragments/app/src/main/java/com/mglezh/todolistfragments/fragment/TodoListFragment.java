package com.mglezh.todolistfragments.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mglezh.todolistfragments.DetailActivity;
import com.mglezh.todolistfragments.R;
import com.mglezh.todolistfragments.model.ToDo;
import com.mglezh.todolistfragments.Adapters.ToDoAdapter;

import java.util.ArrayList;

//import com.mglezh.todolistfragments.fragment.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */

public class TodoListFragment extends ListFragment implements  InputFragment.TODOItemListener {

    private ArrayList<ToDo> todos;
    private ToDoAdapter aa;

    private String TODOS_KEY = "todos";
    public static final String TODO_ITEM = "TODO_ITEM";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        todos = new ArrayList<>();
        aa = new ToDoAdapter(getActivity(), R.layout.todo_list_item, todos);

        if (savedInstanceState != null)  {
            ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
            todos.addAll(tmp);
        }

        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent	=	new	Intent(getActivity(), DetailActivity.class);

        ToDo todo = todos.get(position);

        detailIntent.putExtra(TODO_ITEM, todo);
        startActivity(detailIntent);

        //Para borrar el elemento en el que se hizo click
        //todos.remove(position);
        //aa.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(TODOS_KEY, todos);

        super.onSaveInstanceState(outState);

    }

    @Override
    public void addTodo(ToDo todo) {
        todos.add(0,todo);
        aa.notifyDataSetChanged();
    }
}
