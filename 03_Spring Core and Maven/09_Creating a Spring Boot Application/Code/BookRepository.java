package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository; // Core Spring Data JPA interface

// JpaRepository provides out-of-the-box CRUD (Create, Read, Update, Delete) operations.
// The first generic parameter (Book) is the entity type this repository handles.
// The second generic parameter (Long) is the data type of the entity's primary key (Book.id is Long).
public interface BookRepository extends JpaRepository<Book, Long> {
    // Spring Data JPA automatically generates implementations for standard CRUD methods
    // (e.g., save(), findById(), findAll(), deleteById(), etc.).
    // You can add custom query methods here, and Spring Data JPA will generate
    // the implementation based on the method name, e.g.:
    // List<Book> findByTitleContaining(String title);
    // Optional<Book> findByIsbn(String isbn);
}
