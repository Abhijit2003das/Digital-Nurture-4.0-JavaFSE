package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired; // NEW: Import @Autowired
import org.springframework.stereotype.Service; // NEW: Import @Service
import java.util.List;

@Service // NEW: Mark this class as a Spring Service component
public class BookService {

    // NEW: Use @Autowired for dependency injection (field injection)
    // Spring will automatically find and inject the BookRepository bean here.
    @Autowired
    private BookRepository bookRepository;

    // The default constructor is still implicitly provided by Java,
    // or you could keep an explicit one. Spring doesn't strictly need it here
    // because @Autowired handles the injection.
    public BookService() {
        System.out.println("BookService instance created by component scan!");
    }

    // You can remove the explicit setter if you are using field injection.
    // If you prefer setter injection with annotations, you would put @Autowired above the setter.
    // public void setBookRepository(BookRepository bookRepository) {
    //     this.bookRepository = bookRepository;
    //     System.out.println("BookRepository injected into BookService via setter!");
    // }

    public List<String> getAvailableBooks() {
        System.out.println("BookService: Getting available books...");
        if (bookRepository == null) {
            System.err.println("Error: BookRepository not injected!");
            return List.of("No books available (repository not set)");
        }
        return bookRepository.getAllBookTitles();
    }
}
