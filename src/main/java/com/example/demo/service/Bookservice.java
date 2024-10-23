package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.repo.BookRepo;

@Service
public class Bookservice {
	@Autowired
	BookRepo bookRepo;

	public Book addBook(Book book) {
		Book b = this.bookRepo.save(book);
		return b;
	}

	public List<Book> getAllBooks() {
		List<Book> books = this.bookRepo.findAll();
		return books;
	}

	public Optional<Book> getBookById(long id) {
		Optional<Book> book = null;
		book = bookRepo.findById(id);
		return book;
	}

	public void deleteBook(long id) {
		bookRepo.deleteById(id);
	}

	public void updateBook(Book book, long id) {
		book.setId(id);
		bookRepo.save(book);
	}

}
