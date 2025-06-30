    package com.library.repository;

    import java.util.Arrays; // Already there, but good to note
    import java.util.List; // <<< Make sure this line is present!

    public class BookRepository {

        public BookRepository() {
            System.out.println("BookRepository instance created (XML defined)!");
        }

        public List<String> getAllBookTitles() {
            System.out.println("BookRepository: Fetching all book titles...");
            return Arrays.asList("The Great Gatsby", "1984", "To Kill a Mockingbird", "Pride and Prejudice");
        }
    }
    