package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

// This class provides business logic related to books.
public class BookService {

    private BookRepository bookRepository; // Dependency on BookRepository

    // Default, no-argument constructor is required for setter injection.
    // Spring creates the bean instance using this constructor first.
    public BookService() {
        System.out.println("BookService instance created (default constructor)!");
    }

    // Setter method for dependency injection.
    // Spring will call this method after creating the BookService instance
    // to inject the 'bookRepository' bean.
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected into BookService via setter!");
    }

    // A method to retrieve book titles using the repository.
    public List<String> getAvailableBooks() {
        System.out.println("BookService: Getting available books...");
        // Ensure bookRepository is not null before using it
        if (bookRepository == null) {
            System.err.println("Error: BookRepository not injected!");
            return List.of("No books available (repository not set)");
        }
        return bookRepository.getAllBookTitles();
    }
}
