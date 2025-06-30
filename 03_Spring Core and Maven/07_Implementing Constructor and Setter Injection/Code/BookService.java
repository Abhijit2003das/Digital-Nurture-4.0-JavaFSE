    package com.library.service;

    import com.library.repository.BookRepository;
    import java.util.List; // <<< Make sure this line is present!

    public class BookService {

        private BookRepository bookRepository;
        private String applicationName;

        public BookService(String applicationName) {
            this.applicationName = applicationName;
            System.out.println("BookService instance created via constructor with applicationName: " + applicationName);
        }

        public void setBookRepository(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
            System.out.println("BookRepository injected into BookService via setter!");
        }

        public String getApplicationName() {
            return applicationName;
        }

        public List<String> getAvailableBooks() {
            System.out.println("BookService (" + applicationName + "): Getting available books...");
            if (bookRepository == null) {
                System.err.println("Error: BookRepository not injected!");
                return List.of("No books available (repository not set)");
            }
            return bookRepository.getAllBookTitles();
        }
    }
    