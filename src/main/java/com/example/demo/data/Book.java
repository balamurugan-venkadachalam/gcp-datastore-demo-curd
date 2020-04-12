package com.example.demo.data;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;


@Entity(name = "books")
public class Book {
        @Id
        Long id;

        String title;

        String author;

        int year;

        public Book(String title, String author, int year) {
                this.title = title;
                this.author = author;
                this.year = year;
        }

        public long getId() {
                return this.id;
        }

        @Override
        public String toString() {
                return "Book{" +
                                "id=" + this.id +
                                ", title='" + this.title + '\'' +
                                ", author='" + this.author + '\'' +
                                ", year=" + this.year +
                                '}';
        }
}