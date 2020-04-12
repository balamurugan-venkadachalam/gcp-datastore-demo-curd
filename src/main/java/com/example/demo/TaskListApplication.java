package com.example.demo;

import com.example.demo.service.TaskListService;
import com.example.demo.service.TaskListServiceImpl;
import com.google.cloud.datastore.Key;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@SpringBootApplication
public class TaskListApplication {

    @Autowired
    private TaskListService taskList;

    public static void main(String[] args) {
        SpringApplication.run(TaskListApplication.class, args);

    }

    @ShellMethod("Returns a list of all task entities in ascending order of creation time: list-tasks")
    public String listTasks() {

        List<String> tasks = TaskListServiceImpl.formatTasks(taskList.listTasks());
        System.out.printf("found %d tasks:%n", tasks.size());
        System.out.println("task ID : description");
        return Lists.newArrayList(tasks).toString();
    }

    @ShellMethod("Adds a task entity to the Datastore: save-task <description>")
    public Key saveTask(String description) {
        Key key = taskList.addTask(description);
        return key;
    }

    @ShellMethod("Deletes a task entity: delete-task <id>")
    public void deleteTask(long id) {
        taskList.deleteTask(id);
    }

    @ShellMethod("Marks a task entity as done: mask-done <id>")
    public void markDone(long id) {
        taskList.markDone(id);
    }


}