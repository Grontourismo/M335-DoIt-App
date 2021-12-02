package ch.mgb.uek.m335_doit_app;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void createTodo() {
        Data.createNewTodo("Einkaufen gehen", LocalDate.now());
        assertNotNull(Data.getTodos().get(0));
    }

    @Test
    public void createTodoTitleOnly() {
        Data.createNewTodo("Einkaufen gehen");
        assertNotNull(Data.getTodos().get(0));
    }
}