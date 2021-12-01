package ch.mgb.uek.m335_doit_app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import ch.mgb.uek.m335_doit_app.entity.ToDo;

public class Data {
    private static ArrayList<ToDo> todos = new ArrayList<>();
    private static final String JSON_FILENAME = "";

    public static void createNewTodo(String title, LocalDate dueDate){
        todos.add(new ToDo(title, dueDate));
    }

    public static void createNewTodo(String title){
        todos.add(new ToDo(title));
    }

    public static boolean getAllTodos(){
        boolean bool = true;
        try {
            Gson gson = new Gson();
            gson.toJson(todos, new FileWriter(JSON_FILENAME));
        } catch (IOException e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }

    public static boolean saveAllTodos(){
        boolean bool = true;
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(JSON_FILENAME));
            todos = gson.fromJson(reader, new TypeToken<ArrayList<ToDo>>(){}.getType());
        } catch (FileNotFoundException e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }

    public static int getNextTodoId(){
        return todos.size();
    }

    public static ArrayList<ToDo> getTodos() {
        return todos;
    }
}
