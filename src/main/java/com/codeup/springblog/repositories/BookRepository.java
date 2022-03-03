package com.codeup.springblog.repositories;

import org.springframework.data.jpa.repository.*;
import com.codeup.springblog.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    List<Book> findAll(Book title);
    // Will return a SINGLE book with the matching title.
    Book findBookByTitle(String title);
//
//
//    // Custom queries may require you to use the @Query annotation, this uses HQL syntax.
//    @Query("from Book b where b.title like %:bookTitle%")
//    List<Book> getBookByAuthor(@Param("bookTitle") String title);
}