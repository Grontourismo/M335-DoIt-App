package ch.mgb.uek.m335_doit_app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import ch.mgb.uek.m335_doit_app.Data;
import ch.mgb.uek.m335_doit_app.R;
import ch.mgb.uek.m335_doit_app.TodoType;

public class TodoFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);
    }

    public void clicked(View view){
        EditText titleET = findViewById(R.id.titleInput);
        EditText dateET = findViewById(R.id.dateInput);
        String title = titleET.getText().toString();
        String date = dateET.getText().toString();

        if (validate(title, date, titleET, dateET)){
            if (date.equals("")){
                Data.createNewTodo(title);
            }else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                Data.createNewTodo(title, LocalDate.parse(date, formatter));
            }
            Data.saveAllTodos(this);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("todoType", TodoType.ALL);
            startActivity(intent);
        }
    }

    private boolean validate(String title, String date, EditText titleET, EditText dateET) {
        boolean bool = true;
        if (title.equals("") || title.length() == 0){
            titleET.setError("Darf nicht leer sein");
            bool = false;
        } else if (title.length() > 40){
            titleET.setError("Titel darf nicht länger als 46 sein");
            bool = false;
        }

        if (!date.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate localDate = LocalDate.parse(date, formatter);
                if (!localDate.isAfter(LocalDate.now().minusDays(1))){
                    dateET.setError("Datum kann frühstens heute sein");
                    bool = false;
                }
            } catch (DateTimeParseException e) {
                dateET.setError("Muss in Datumformat sein (tt.MM.jjjj)");
                bool = false;
            }
        }

        return bool;
    }
}
