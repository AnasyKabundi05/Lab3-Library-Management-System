package com.example.demo.service;
import com.example.demo.domain.Library;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    public Library getLibraryById(Long id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Library not found with id " + id));
    }

    public Library createLibrary(Library library) {
        // Keep it simple; could validate here if desired
        return libraryRepository.save(library);
    }

    // Extension challenge #2: prevent deletion if it still has books
    @Transactional
    public void deleteLibrary(Long id) throws BadRequestException {
        if (!libraryRepository.existsById(id)) {
            throw new NotFoundException("Library not found with id " + id);
        }

        long bookCount = bookRepository.countByLibraryId(id);

        if (bookCount > 0) {
            throw new BadRequestException("Cannot delete library " + id + " because it still has " + bookCount + " book(s).");
        }

        libraryRepository.deleteById(id);
    }
}
