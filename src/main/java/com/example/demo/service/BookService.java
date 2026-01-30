package com.example.demo.service;
import com.example.demo.domain.Book;
import com.example.demo.domain.Library;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByLibrary(Long libraryId) {
        // Optional: verify library exists for nicer error
        if (!libraryRepository.existsById(libraryId)) {
            throw new NotFoundException("Library not found with id " + libraryId);
        }
        return bookRepository.findByLibraryId(libraryId);
    }

    public Book createBook(Long libraryId, Book book) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new NotFoundException("Library not found with id " + libraryId));

        book.setLibrary(library);
        return bookRepository.save(book);
    }

    // Extension challenge #1: delete a Book endpoint support
    public void deleteBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException("Book not found with id " + bookId);
        }
        bookRepository.deleteById(bookId);
    }
}