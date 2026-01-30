package com.example.demo.api;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Base path: /books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // GET /libraries/{id}/books
    @GetMapping("/libraries/{id}/books")
    public List<Book> getBooksByLibrary(@PathVariable Long id) {
        return bookService.getBooksByLibrary(id);
    }

    // POST /libraries/{id}/books
    @PostMapping("/libraries/{id}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBookToLibrary(@PathVariable Long id, @Validated @RequestBody Book book) {
        return bookService.createBook(id, book);
    }

    // Extension challenge #1: DELETE /books/{id}
    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
