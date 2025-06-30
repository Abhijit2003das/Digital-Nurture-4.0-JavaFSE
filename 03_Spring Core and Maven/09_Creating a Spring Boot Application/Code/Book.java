package com.library.model;

import javax.persistence.Entity;        // Marks this class as a JPA entity
import javax.persistence.GeneratedValue; // For auto-generating primary key values
import javax.persistence.GenerationType; // Strategy for auto-generation
import javax.persistence.Id;           // Marks the primary key field

@Entity // This annotation indicates that this class is a JPA entity and maps to a database table.
public class Book {

    @Id // This annotation specifies the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the primary key to be auto-incremented by the database.
    private Long id;
    private String title;
    private String author;
    private String isbn; // International Standard Book Number

    // Default constructor is required by JPA. Hibernate will use this to create instances.
    public Book() {
    }

    // Parameterized constructor for easy object creation (without ID, as it's generated).
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // --- Getters and Setters ---
    // These methods are used by JPA/Hibernate to access and set the entity's properties.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isbn='" + isbn + '\'' +
               '}';
    }
}
