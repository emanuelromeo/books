package com.crud.book.controller;

import com.crud.book.entity.Book;
import com.crud.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Returns all books.
     * @return a response entity containing a list of books.
     */
    @GetMapping("/select-all")
    public ResponseEntity<List<Book>> getAllBooks() {
        //Assegno tutti i libri recuperati dal database alla lista
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Returns a book by its id.
     * @param id
     * @return a response entity containing the book with the given id or ResponseEntity.notFound() if none found.
     */
    @GetMapping("/select-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getBookById(id);

        //Controllo se il libro esiste e lo restituisco
        if (bookOptional.isPresent()) {
            return ResponseEntity.ok(bookOptional.get());
        }


        //Restituisco "Not Found" se il libro non esisteù

        return ResponseEntity.notFound().build();

    }

    /**
     * Saves a given book.
     * @param book
     * @return a response entity containing the saved book.
     */
    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.ok(savedBook);
    }

    /**
     * Saves all given books.
     * @param books
     * @return a response entity containing the saved list of books.
     */
    @PostMapping("/create-all")
    public ResponseEntity<List<Book>> createAllBooks(@RequestBody List<Book> books) {
        List<Book> savedBooks = bookService.createAllBooks(books);
        return ResponseEntity.ok(savedBooks);
    }

    /**
     * Updates a book by its id with values from the given book.
     * @param id
     * @param updatedBook
     * @return a response entity containing the updated book or ResponseEntity.notFound() if none found.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> updatedBookOptional = bookService.updateBook(id, updatedBook);

        //Controllo se il libro è stato aggionato
        if (updatedBookOptional.isPresent()) {
            return ResponseEntity.ok(updatedBookOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Updates a book by its id marking it as "Deleted".
     * @param id
     * @return a response entity containing the deleted book or ResponseEntity.notFound() if none found.
     */
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long id) {
        Optional<Book> deletedBookOptional = bookService.deleteById(id);

        //Controllo se il libro è stato eliminato
        if (deletedBookOptional.isPresent()) {
            return ResponseEntity.ok(deletedBookOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Creates the given number of fake books
     * @param count
     * @return a response entity containing the list of saved books
     */
    @PostMapping("/create-fake-books")
    public ResponseEntity<List<Book>> createFakeBooks(@RequestParam int count) {
        List<Book> savedBooks = bookService.populateDatabase(count);
        return ResponseEntity.ok(savedBooks);
    }

}
