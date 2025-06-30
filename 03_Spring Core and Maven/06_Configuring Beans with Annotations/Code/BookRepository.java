package com.library.repository;

import org.springframework.stereotype.Repository; // NEW: Import @Repository
import java.util.Arrays;
import java.util.List;

@Repository // NEW: Mark this class as a Spring Repository component
public class BookRepository {

    public BookRepository() {
        System.out.println("BookRepository instance created by component scan!");
    }

    public List<String> getAllBookTitles() {
        System.out.println("BookRepository: Fetching all book titles...");
        return Arrays.asList("The Great Gatsby", "1984", "To Kill a Mockingbird", "Pride and Prejudice");
    }
}
