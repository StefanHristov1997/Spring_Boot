package com.example.springintro.repository;

import com.example.springintro.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findBookByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBookByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findBookByPriceLessThanOrPriceGreaterThan(BigDecimal startPrice, BigDecimal endPrice);

    List<Book> findBookByReleaseDateIsNot(LocalDate releaseDate);

    List<Book> findBookByReleaseDateIsBefore(LocalDate releaseDate);

    List<Book> findBookByTitleContainingIgnoreCase(String letters);

    @Query("SELECT b FROM Book AS b " +
            "WHERE b.author.lastName LIKE(:letters)")
    List<Book> findBookByAuthorLastNameStartWith(String letters);

    int countBooksByTitleIsGreaterThan(String title);
    int countCopiesByAuthorOrderByCopiesDesc(Author author);

    BookDTO findBookByTitle(String title);
}
