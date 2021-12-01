package ch.mgb.uek.m335_doit_app.entity;

import java.time.LocalDate;

import ch.mgb.uek.m335_doit_app.Data;

public class ToDo {
    private int id;
    private String title;
    private LocalDate dueDate;

    public ToDo(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        setId();
    }

    public ToDo(String title) {
        this.title = title;
        setId();
    }

    private void setId(){
        this.id = Data.getNextTodoId();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
