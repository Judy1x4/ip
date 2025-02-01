package jude;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import jude.task.Deadline;
import jude.task.Event;
import jude.task.Task;
import jude.task.Todo;

public class TaskListTest {
    @Test
    public void toFileFormat_toFileFormat_success() {
        try {
            List<Task> tasklist = new ArrayList<>();
            tasklist.add(new Todo("AAA"));
            assertEquals("T | 0 | AAA\n", new TaskList(tasklist).toFileFormat());
        } catch (JudeException je) {
            fail();
        }

        try {
            List<Task> tasklist = new ArrayList<>();
            tasklist.add(new Todo("AAA", true));
            assertEquals("T | 1 | AAA\n", new TaskList(tasklist).toFileFormat());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void toFileFormat_multipleTasks_success() {
        try {
            List<Task> tasklist = new ArrayList<>();
            tasklist.add(new Todo("AAA"));
            tasklist.add(new Deadline("BBB", "2000-01-01T00:00", true));
            tasklist.add(new Event("CCC", "2000-01-01T00:00", "2000-01-01T12:00", true));
            assertEquals(
                    "T | 0 | AAA\nD | 1 | BBB | 2000-01-01T00:00\n"
                            + "E | 1 | CCC | 2000-01-01T00:00 | 2000-01-01T12:00\n",
                    new TaskList(tasklist).toFileFormat());
        } catch (JudeException je) {
            je.printStackTrace();
            fail();
        }
    }

    @Test
    public void toFileFormat_empty_success() {
        try {
            List<Task> tasklist = new ArrayList<>();
            assertEquals("", new TaskList(tasklist).toFileFormat());
        } catch (JudeException je) {
            fail();
        }
    }

    @Test
    public void addTask_standard_success() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));

            assertEquals(
                    "T | 0 | AAA\n", tasklist.toFileFormat());
        } catch (JudeException je) {
            je.printStackTrace();
            fail();
        }
    }

    @Test
    public void addTask_multipleTasks_success() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));
            tasklist.addTask(new Deadline("BBB", "2000-01-01T00:00", true));
            tasklist.addTask(new Event(
                    "CCC", "2000-01-01T00:00", "2000-01-01T12:00", true));
            assertEquals(
                    "T | 0 | AAA\nD | 1 | BBB | 2000-01-01T00:00\n"
                            + "E | 1 | CCC | 2000-01-01T00:00 | 2000-01-01T12:00\n",
                    tasklist.toFileFormat());
        } catch (JudeException je) {
            je.printStackTrace();
            fail();
        }
    }

    @Test
    public void deleteTask_standard_success() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));
            tasklist.deleteTask(0);
            assertEquals(
                    "", tasklist.toFileFormat());
        } catch (JudeException je) {
            je.printStackTrace();
            fail();
        }
    }

    @Test
    public void deleteTask_outOfIndex_fail() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));
            tasklist.deleteTask(1);
            assertEquals("", tasklist.toFileFormat());
            fail();
        } catch (JudeException je) {
            assertEquals(
                    "You are trying to get an element of index out of the list size.", je.getMessage());
        }
    }

    @Test
    public void getTask_standard_success() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));
            assertEquals(
                    new Todo("AAA").toString(), tasklist.getTask(0).toString());
        } catch (JudeException je) {
            je.printStackTrace();
            fail();
        }
    }

    @Test
    public void getTask_outOfIndex_fail() {
        try {
            List<Task> list = new ArrayList<>();
            TaskList tasklist = new TaskList(list);
            tasklist.addTask(new Todo("AAA"));
            assertEquals(
                    new Todo("AAA").toString(), tasklist.getTask(1).toString());
            fail();
        } catch (JudeException je) {
            assertEquals("You are trying to get an element of index out of the list size.", je.getMessage());
        }
    }
}
