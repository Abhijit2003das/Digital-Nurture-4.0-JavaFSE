package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

// This class provides business logic related to books.
public class BookService {

    private BookRepository bookRepository; // Dependency on BookRepository

    // Default, no-argument constructor is required for setter injection.
    public BookService() {
        System.out.println("BookService instance created (default constructor)!");
    }

    // Setter method for dependency injection.
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected into BookService via setter!");
    }

    // A method to retrieve book titles using the repository.
    public List<String> getAvailableBooks() {
        System.out.println("BookService: Getting available books...");
        if (bookRepository == null) {
            System.err.println("Error: BookRepository not injected!");
            return List.of("No books available (repository not set)");
        }
        return bookRepository.getAllBookTitles();
    }
}
