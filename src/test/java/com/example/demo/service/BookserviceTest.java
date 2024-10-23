package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.demo.entities.Book;
import com.example.demo.repo.BookRepo;

@SpringBootTest
class BookserviceTest {

	@Mock
	private BookRepo bookRepo;
	
	@InjectMocks
	private Bookservice bookService;

    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Book 1", "Author 1", new BigDecimal("100.00"), LocalDate.of(2020, 5, 10)));
		bookList.add(new Book(2L, "Book 2", "Author 2", new BigDecimal("200.00"), LocalDate.of(2021, 7, 15)));
		bookList.add(new Book(3L, "Book 3", "Author 3", new BigDecimal("300.00"), LocalDate.of(2019, 3, 20)));
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book(3L, "New Book", "New Author", new BigDecimal("150.00"), LocalDate.now());
        when(bookRepo.save(any(Book.class))).thenReturn(newBook);
        Book addedBook = bookService.addBook(newBook);
        assertEquals(newBook, addedBook);
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepo.findAll()).thenReturn(bookList);
        List<Book> result = bookService.getAllBooks();
        assertEquals(3, result.size());
        assertEquals(bookList, result);
    }
    
    @Test
    public void testGetBookById() {
    	when(bookRepo.findById(1L)).thenReturn(Optional.of(bookList.get(0)));
        Optional<Book> result = bookService.getBookById(1L);
        assertEquals(bookList.get(0), result.get());
    }

    @Test
    public void testDeleteBook() {
    	doNothing().when(bookRepo).deleteById(1L);
        bookService.deleteBook(1L);
        when(bookRepo.findAll()).thenReturn(bookList);
        List<Book> result = bookService.getAllBooks();
        assertEquals(bookList.size(), result.size());
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book(1L, "Updated Book", "Updated Author", new BigDecimal("170.00"), LocalDate.of(2022, 9, 30));
        when(bookRepo.save(any(Book.class))).thenReturn(updatedBook);
        bookService.updateBook(updatedBook, 1L);
        assertEquals(1L, updatedBook.getId());
    }

}
