package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

// This class provides business logic related to books.
public class BookService {

    private BookRepository bookRepository; // Dependency on BookRepository

    // Constructor for dependency injection. Spring will inject BookRepository here.
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService instance created with BookRepository!");
    }

    // A method to retrieve book titles using the repository.
    public List<String> getAvailableBooks() {
        System.out.println("BookService: Getting available books...");
        return bookRepository.getAllBookTitles();
    }

    // We can also have a default constructor if we were using setter injection
    // public BookService() {
    //     System.out.println("BookService instance created (default constructor)!");
    // }

    // public void setBookRepository(BookRepository bookRepository) {
    //     this.bookRepository = bookRepository;
    //     System.out.println("BookRepository set in BookService!");
    // }
}
