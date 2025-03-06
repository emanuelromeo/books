package com.crud.book.service;

import com.crud.book.entity.Book;
import com.crud.book.enumerate.RecordStatus;
import com.crud.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Returns all books.
     * @return a list of books.
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Returns a book by its id.
     * @param id
     * @return an optional containing the book with the given id or Optional.empty() if none found.
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Saves a given book.
     * @param book
     * @return the saved book.
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Saves all given books.
     * @param books
     * @return the saved list of books.
     */
    public List<Book> createAllBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    /**
     * Updates a book by its id with values from the given book.
     * @param id
     * @param updatedBook
     * @return an optional containing the updated book or Optional.empty() if none found.
     */
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> currentBookOptional = bookRepository.findById(id);

        //Controllo se il libro esiste
        if (currentBookOptional.isPresent()) {
            currentBookOptional.get().setAuthor(updatedBook.getAuthor());
            currentBookOptional.get().setIsbn(updatedBook.getIsbn());
            currentBookOptional.get().setTitle(updatedBook.getTitle());

            //Salvo il libro aggiornato sul database e lo restituisco
            Book savedBook = bookRepository.save(currentBookOptional.get());
            return Optional.of(savedBook);
        }

        return Optional.empty();
    }

    /**
     * Updates a book by its id marking it as "Deleted".
     * @param id
     * @return an optional containing the deleted book or Optional.empty() if none found.
     */
    public Optional<Book> deleteById(Long id) {
        Optional<Book> currentBookOptional = bookRepository.findById(id);

        //Controllo se il libro esiste
        if (currentBookOptional.isPresent()) {
            currentBookOptional.get().setRecordStatus(RecordStatus.D);

            //Salvo il libro eliminato sul database e lo restiuisco
            Book deletedBook = bookRepository.save(currentBookOptional.get());
            return Optional.of(deletedBook);
        }

        return Optional.empty();
    }

    /**
     * Creates the given number of fake books
     * @param count
     * @return the list of saved books
     */
    public List<Book> populateDatabase(int count) {
        List<Book> books = new ArrayList<>();

        //Ciclo per il numero di volte passato
        for (int i = 0; i < count; i++) {

            //Creo un isbn randomico
            String isbn = UUID.randomUUID().toString();

            //Creo e salvo sul database un libro falso
            Book fakeBook = new Book(null, "Book #" + i, "Author #" + i, isbn);
            Book savedBook = bookRepository.save(fakeBook);

            //Aggiungo il libro salvato alla lista
            books.add(savedBook);
        }

        //Restituisco la lista dei libri
        return books;
    }

}
