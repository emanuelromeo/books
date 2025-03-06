package com.crud.book.repository;

import com.crud.book.entity.Book;
import com.crud.book.enumerate.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
