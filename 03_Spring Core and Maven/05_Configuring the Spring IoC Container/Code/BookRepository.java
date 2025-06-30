package com.library.repository;

import java.util.Arrays;
import java.util.List;

// This class simulates a data repository for books.
public class BookRepository {

    public BookRepository() {
        System.out.println("BookRepository instance created!");
    }

    public List<String> getAllBookTitles() {
        System.out.println("BookRepository: Fetching all book titles...");
        return Arrays.asList("The Great Gatsby", "1984", "To Kill a Mockingbird", "Pride and Prejudice");
    }
}
