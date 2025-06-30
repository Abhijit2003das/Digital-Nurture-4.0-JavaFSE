package com.library.controller;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    public void initData() {
        if (bookRepository.count() == 0) {
            System.out.println("Initializing sample book data...");
            bookRepository.save(new Book("The Alchemist", "Paulo Coelho", "978-0061122415"));
            bookRepository.save(new Book("The Subtle Art of Not Giving a F*ck", "Mark Manson", "978-0062457714"));
            bookRepository.save(new Book("Atomic Habits", "James Clear", "978-0735211292"));
            System.out.println("Sample book data initialized.");
        }
    }

    @GetMapping
    public List<Book> getAllBooks() {
        System.out.println("Received GET /api/books request.");
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        System.out.println("Received GET /api/books/" + id + " request.");
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        System.out.println("Received POST /api/books request with body: " + book);
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        System.out.println("Received PUT /api/books/" + id + " request with body: " + bookDetails);
        return bookRepository.findById(id)
            .map(book -> {
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setIsbn(bookDetails.getIsbn());
                Book updatedBook = bookRepository.save(book);
                return ResponseEntity.ok(updatedBook);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Corrected DELETE method
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        System.out.println("Received DELETE /api/books/" + id + " request.");
        if (bookRepository.existsById(id)) { // Check if book exists first
            bookRepository.deleteById(id); // Delete by ID directly
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if ID doesn't exist
        }
    }
}
