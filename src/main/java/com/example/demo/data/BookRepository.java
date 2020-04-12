package com.example.demo.data;

import java.util.List;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;


public interface BookRepository extends DatastoreRepository<Book, Long> {

  List<Book> findByAuthor(String author);

  List<Book> findByYearGreaterThan(int year);

  List<Book> findByAuthorAndYear(String author, int year);

}