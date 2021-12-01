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
    private static final String JSON_FILEPATH = "ch/mgb/uek/m335_doit_app/data/todos.json";

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
            gson.toJson(todos, new FileWriter(JSON_FILEPATH));
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
            JsonReader reader = new JsonReader(new FileReader(JSON_FILEPATH));
            todos = gson.fromJson(reader, new TypeToken<ArrayList<ToDo>>(){}.getType());
        } catch (FileNotFoundException e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }

    public static ArrayList<ToDo> getTodosOfToday(boolean isFinished){
        ArrayList<ToDo> returnToDos = new ArrayList<>();
        for (ToDo todo : todos){
            LocalDate test = LocalDate.now();
            if (todo.getDueDate().equals(LocalDate.now()) && todo.isFinished() == isFinished) {
                returnToDos.add(todo);
            }
        }
        return returnToDos;
    }

    public static ArrayList<ToDo> getTodos(boolean isFinished){
        ArrayList<ToDo> returnToDos = new ArrayList<>();
        for (ToDo todo : todos){
            if (todo.isFinished() == isFinished) {
                returnToDos.add(todo);
            }
        }
        return returnToDos;
    }

    public static int getNextTodoId(){
        return todos.size() + 1;
    }
}
