package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entities.Book;
import com.example.demo.service.Bookservice;

@SpringBootTest
class BookControllerTest {

	@Mock
	private Bookservice bookService;

	@InjectMocks
	private BookController bookController;

	private List<Book> bookList;

	@BeforeEach
	public void before() {
		bookList = new ArrayList<>();
		bookList.add(new Book(1L, "Book 1", "Author 1", new BigDecimal("100.00"), LocalDate.of(2020, 5, 10)));
		bookList.add(new Book(2L, "Book 2", "Author 2", new BigDecimal("200.00"), LocalDate.of(2021, 7, 15)));
		bookList.add(new Book(3L, "Book 3", "Author 3", new BigDecimal("300.00"), LocalDate.of(2019, 3, 20)));
	}

	@Test
	void testAddBook() {
		Book newBook = new Book(4L, "New Book", "New Author", new BigDecimal("400.00"), LocalDate.now());
		when(bookService.addBook(newBook)).thenReturn(newBook);
		ResponseEntity<Book> response = bookController.addBook(newBook);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(newBook, response.getBody());
	}

	@Test
	void testGetBooks() {
		when(bookService.getAllBooks()).thenReturn(bookList);
		ResponseEntity<List<Book>> response = bookController.getBooks();
		assertEquals(bookList, response.getBody());
	}

	@Test
	void testGetBook() {
		when(bookService.getBookById(1L)).thenReturn(Optional.of(bookList.get(0)));
	    ResponseEntity<Optional<Book>> response = bookController.getBook(1L);
	    assertEquals(Optional.of(bookList.get(0)), response.getBody());
	}

	@Test
	void testDeleteBook() {
		doNothing().when(bookService).deleteBook(2L);
		ResponseEntity<Void> response = bookController.deleteBook(1);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void testUpdateBook() {
		Book updatedBook = new Book(1L, "Updated Book", "Updated Author", new BigDecimal("170.00"),
	            LocalDate.of(2022, 9, 30));
	    doNothing().when(bookService).updateBook(updatedBook, 1L);
	    ResponseEntity<Book> response = bookController.updateBook(updatedBook, 1L);
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertEquals(updatedBook, response.getBody());
	}

}
