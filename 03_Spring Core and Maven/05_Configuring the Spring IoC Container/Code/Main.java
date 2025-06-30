package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// This is the main class to run our Spring application.
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting the Spring application...");

        // 1. Load the Spring application context from the XML file.
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Spring ApplicationContext loaded successfully.");

        // 2. Retrieve the BookService bean from the context.
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("BookService bean retrieved from context.");

        // 3. Use the BookService to get book titles.
        System.out.println("\n--- Available Books ---");
        bookService.getAvailableBooks().forEach(System.out::println);
        System.out.println("-----------------------\n");

        // 4. Close the context.
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("Spring ApplicationContext closed.");
    }
}
