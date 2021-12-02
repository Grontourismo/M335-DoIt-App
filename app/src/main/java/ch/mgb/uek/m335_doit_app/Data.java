package ch.mgb.uek.m335_doit_app;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import ch.mgb.uek.m335_doit_app.entity.ToDo;

public class Data {
    private static ArrayList<ToDo> todos = new ArrayList<>();
    private static final String JSON_FILENAME = "todos.json";

    public static void createNewTodo(String title, LocalDate dueDate){
        todos.add(new ToDo(title, dueDate));
    }

    public static void createNewTodo(String title){
        todos.add(new ToDo(title));
    }

    public static void getAllTodos(Context context){
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                    .create();
            InputStream inputStream = context.openFileInput(JSON_FILENAME);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ArrayList<ToDo> list = gson.fromJson(stringBuilder.toString(), new TypeToken<ArrayList<ToDo>>(){}.getType());
                todos = list != null ? list : new ArrayList<>();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public static void saveAllTodos(Context context) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .create();
            String jsonString = gson.toJson(todos);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(JSON_FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static ArrayList<ToDo> getTodosOfToday(boolean isFinished){
        ArrayList<ToDo> returnToDos = new ArrayList<>();
        for (ToDo todo : todos){
            if (todo.getDueDate() != null) {
                if (todo.getDueDate().equals(LocalDate.now()) && todo.isFinished() == isFinished) {
                    returnToDos.add(todo);
                }
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

    static class LocalDateSerializer implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            if (date != null) {
                return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            }
            return null;
        }
    }

    static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (json != null) {
                return LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            return null;
        }
    }
}
