package com.example.bookshop.repository;

import com.example.bookshop.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource

public interface BookRepository extends JpaRepository<Book, Long> {

}
