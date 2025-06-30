package com.library.repository;

import java.util.Arrays;
import java.util.List;

// This class simulates a data repository for books.
public class BookRepository {

    // A simple method to get a list of book titles.
    public List<String> getAllBookTitles() {
        System.out.println("BookRepository: Fetching all book titles...");
        return Arrays.asList("The Great Gatsby", "1984", "To Kill a Mockingbird", "Pride and Prejudice");
    }

    // A default constructor (important for Spring bean instantiation).
    public BookRepository() {
        System.out.println("BookRepository instance created!");
    }
}
