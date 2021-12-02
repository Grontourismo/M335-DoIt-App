package ch.mgb.uek.m335_doit_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;

import ch.mgb.uek.m335_doit_app.Data;
import ch.mgb.uek.m335_doit_app.R;
import ch.mgb.uek.m335_doit_app.TodoType;
import ch.mgb.uek.m335_doit_app.adabter.ToDoListAdapter;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance;
    private TodoType type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data.getAllTodos(this);
        instance = this;
        Intent mainIntent = getIntent();
        try {
            type = (TodoType) mainIntent.getSerializableExtra("todoType");
        } catch (NullPointerException e){
            type = TodoType.TODAY;
        }
        setContentView(R.layout.activity_main);
        setItems();
    }

    public void setItems(){
        ListView view = findViewById(R.id.view);
        ListView doneView = findViewById(R.id.doneView);
        if (type == TodoType.TODAY || type == null) {
            ((TextView) findViewById(R.id.stateTextView)).setText(R.string.today);
            ((TextView) findViewById(R.id.stateDoneTextView)).setText(R.string.today_done);
            view.setAdapter(new ToDoListAdapter(this, R.layout.fragment_todo, Data.getTodosOfToday(false)));
            doneView.setAdapter(new ToDoListAdapter(this, R.layout.fragment_todo, Data.getTodosOfToday(true)));
        }
        else if (type == TodoType.ALL){
            ((TextView) findViewById(R.id.stateTextView)).setText(R.string.all);
            ((TextView) findViewById(R.id.stateDoneTextView)).setText(R.string.all_done);
            view.setAdapter(new ToDoListAdapter(this, R.layout.fragment_todo, Data.getTodos(false)));
            doneView.setAdapter(new ToDoListAdapter(this, R.layout.fragment_todo, Data.getTodos(true)));
        }
    }

    public void reload(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void todoFormClicked(View view){
        startTodoForm();
    }

    private void startTodoForm() {
        Intent intent = new Intent(this, TodoFormActivity.class);
        intent.putExtra("todoType", type);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void newTodoClicked(MenuItem item) {
        startTodoForm();
    }

    public void todayToDosClicked(MenuItem item) {
        type = TodoType.TODAY;
        setItems();
    }

    public void allToDosClicked(MenuItem item) {
        type = TodoType.ALL;
        setItems();
    }
}