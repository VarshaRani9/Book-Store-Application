package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.repo.BookRepo;

@Service
public class Bookservice {

	private static final Logger LOGGER = LoggerFactory.getLogger(Bookservice.class);

	@Autowired
	BookRepo bookRepo;

	public Book addBook(Book book) {
		LOGGER.info("Adding a new book: {}", book.getTitle());
		Book b = bookRepo.save(book);
		LOGGER.debug("Book added successfully: {}", b);
		return b;
	}

	public List<Book> getAllBooks() {
		LOGGER.info("Fetching all books from the database");
		List<Book> books = bookRepo.findAll();
		LOGGER.debug("Number of books fetched: {}", books.size());
		return books;
	}

	public Optional<Book> getBookById(long id) {
		LOGGER.info("Fetching book with ID: {}", id);
		Optional<Book> book = bookRepo.findById(id);
		if (book.isPresent()) {
			LOGGER.debug("Book found: {}", book.get());
		} else {
			LOGGER.warn("No book found with ID: {}", id);
		}
		return book;
	}

	public void deleteBook(long id) {
		LOGGER.info("Deleting book with ID: {}", id);
		bookRepo.deleteById(id);
		LOGGER.debug("Book with ID {} deleted successfully", id);
	}

	public void updateBook(Book book, long id) {
		LOGGER.info("Updating book with ID: {}", id);
		book.setId(id);
		Book updatedBook = bookRepo.save(book);
		LOGGER.debug("Book updated successfully: {}", updatedBook);

	}
}
