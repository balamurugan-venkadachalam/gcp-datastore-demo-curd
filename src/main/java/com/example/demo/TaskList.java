package com.example.demo;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple Task List application demonstrating how to connect to Cloud Datastore, create, modify,
 * delete, and query entities.
 */
@Component
public class TaskList {

  // [START datastore_build_service]
  // Create an authorized Datastore service using Application Default Credentials.
  private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

  // Create a Key factory to construct keys associated with this project.
  private final KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");
  // [END datastore_build_service]

  // [START datastore_add_entity]
  /**
   * Adds a task entity to the Datastore.
   *
   * @param description The task description
   * @return The {@link Key} of the entity
   * @throws DatastoreException if the ID allocation or put fails
   */
  public Key addTask(String description) {
    Key key = datastore.allocateId(keyFactory.newKey());
    Entity task = Entity.newBuilder(key)
        .set("description", StringValue.newBuilder(description).setExcludeFromIndexes(true).build())
        .set("created", Timestamp.now())
        .set("done", false)
        .build();
    datastore.put(task);
    return key;
  }
  // [END datastore_add_entity]

  // [START datastore_update_entity]
  /**
   * Marks a task entity as done.
   *
   * @param id The ID of the task entity as given by {@link Key#id()}
   * @return true if the task was found, false if not
   * @throws DatastoreException if the transaction fails
   */
  public boolean markDone(long id) {
    Transaction transaction = datastore.newTransaction();
    try {
      Entity task = transaction.get(keyFactory.newKey(id));
      if (task != null) {
        transaction.put(Entity.newBuilder(task).set("done", true).build());
      }
      transaction.commit();
      return task != null;
    } finally {
      if (transaction.isActive()) {
        transaction.rollback();
      }
    }
  }
  // [END datastore_update_entity]

  // [START datastore_retrieve_entities]
  /**
   * Returns a list of all task entities in ascending order of creation time.
   *
   * @throws DatastoreException if the query fails
   */
  public Iterator<Entity> listTasks() {
    Query<Entity> query =
        Query.newEntityQueryBuilder().setKind("Task").setOrderBy(OrderBy.asc("created")).build();
    return datastore.run(query);
  }
  // [END datastore_retrieve_entities]

  // [START datastore_delete_entity]
  /**
   * Deletes a task entity.
   *
   * @param id The ID of the task entity as given by {@link Key#id()}
   * @throws DatastoreException if the delete fails
   */
  public void deleteTask(long id) {
    datastore.delete(keyFactory.newKey(id));
  }
  // [END datastore_delete_entity]

  /**
   * Converts a list of task entities to a list of formatted task strings.
   *
   * @param tasks An iterator over task entities
   * @return A list of tasks strings, one per entity
   */
  static List<String> formatTasks(Iterator<Entity> tasks) {
    List<String> strings = new ArrayList<>();
    while (tasks.hasNext()) {
      Entity task = tasks.next();
      if (task.getBoolean("done")) {
        strings.add(
            String.format("%d : %s (done)", task.getKey().getId(), task.getString("description")));
      } else {
        strings.add(String.format("%d : %s (created %s)", task.getKey().getId(),
            task.getString("description"), task.getTimestamp("created")));
      }
    }
    return strings;
  }
}