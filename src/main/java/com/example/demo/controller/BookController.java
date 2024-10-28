package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Book;
import com.example.demo.service.Bookservice;


@RestController
public class BookController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	Bookservice bookService;
	
	@PostMapping("/books")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book b;
		try {
            LOGGER.info("Adding new book: {}", book.getTitle());
            b = bookService.addBook(book);
            LOGGER.debug("Book added successfully: {}", b);
            return ResponseEntity.status(HttpStatus.CREATED).body(b);
        } catch (Exception e) {
            LOGGER.error("Error adding book: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		LOGGER.info("Fetching all books");
        List<Book> list = bookService.getAllBooks();
        if (list.isEmpty()) {
            LOGGER.warn("No books found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LOGGER.debug("Books fetched successfully. Count: {}", list.size());
        return ResponseEntity.ok(list);
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Optional<Book>> getBook(@PathVariable("id") long id) {
		LOGGER.info("Fetching book with ID: {}", id);
        Optional<Book> book = bookService.getBookById(id);
        if (book.isEmpty()) {
            LOGGER.warn("Book not found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        LOGGER.debug("Book found: {}", book.get());
//        return ResponseEntity.ok(book);
		return ResponseEntity.of(Optional.of(book));
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") long bookId) {
		try {
            LOGGER.info("Deleting book with ID: {}", bookId);
            bookService.deleteBook(bookId);
            LOGGER.debug("Book deleted successfully: ID {}", bookId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting book with ID {}: {}", bookId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable("id") long bookId) {
		try {
            LOGGER.info("Updating book with ID: {}", bookId);
            bookService.updateBook(book, bookId);
            LOGGER.debug("Book updated successfully: {}", book);
            return ResponseEntity.ok().body(book);
        } catch (Exception e) {
            LOGGER.error("Error updating book with ID {}: {}", bookId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}

}
