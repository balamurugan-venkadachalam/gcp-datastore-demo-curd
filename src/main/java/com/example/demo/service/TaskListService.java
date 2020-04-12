package com.example.demo.service;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import java.util.Iterator;

public interface TaskListService {
    Key addTask(String description);

    boolean markDone(long id);

    Iterator<Entity> listTasks();

    void deleteTask(long id);


}
