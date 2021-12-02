package ch.mgb.uek.m335_doit_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import ch.mgb.uek.m335_doit_app.Data;
import ch.mgb.uek.m335_doit_app.R;
import ch.mgb.uek.m335_doit_app.TodoType;

public class TodoFormActivity extends AppCompatActivity {
    private TodoType type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);
        Intent mainIntent = getIntent();
        type = (TodoType) mainIntent.getSerializableExtra("todoType");
    }

    public void clicked(View view){
        EditText titleET = findViewById(R.id.titleInput);
        DatePicker dateET = findViewById(R.id.dateInput);
        String title = titleET.getText().toString();
        String date = dateET.getDayOfMonth() + "." + (dateET.getMonth() + 1) + "." + dateET.getYear();

        if (validate(title, date, titleET)){
            if (date.equals("")){
                Data.createNewTodo(title);
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
                Data.createNewTodo(title, LocalDate.parse(date, formatter));
            }
            Data.saveAllTodos(this);
            startLastActivity();
        }
    }

    private void startLastActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("todoType", type);
        startActivity(intent);
    }

    private boolean validate(String title, String date, EditText titleET) {
        boolean bool = true;
        if (title.equals("") || title.length() == 0){
            titleET.setError("Darf nicht leer sein");
            bool = false;
        } else if (title.length() > 40){
            titleET.setError("Titel darf nicht länger als 46 sein");
            bool = false;
        }

        if (!date.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            try {
                LocalDate localDate = LocalDate.parse(date, formatter);
                LocalDate test = LocalDate.now().minusDays(1);
                if (!localDate.isAfter(LocalDate.now().minusDays(1))){
                    ((TextView) findViewById(R.id.errorTextView)).setText("Datum kann frühstens heute sein");
                    bool = false;
                }
            } catch (DateTimeParseException e) {
                ((TextView) findViewById(R.id.errorTextView)).setText("Muss in Datumformat sein (tt.MM.jjjj)");
                bool = false;
            }
        }

        return bool;
    }
}
