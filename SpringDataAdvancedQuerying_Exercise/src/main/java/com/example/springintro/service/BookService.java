package com.example.springintro.service;

import com.example.springintro.model.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findBookByAgeRestriction(AgeRestriction ageRestriction);
    List<Book> findBookByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);
    List<Book> findBookByPriceLessThanOrPriceGreaterThan(BigDecimal startPrice, BigDecimal endPrice);
    List<Book> findBookByReleaseDateIsNot(LocalDate releaseDate);
    List<Book> findBookByReleaseDateIsBefore(LocalDate releaseDate);
    List<Book> findBookByTitleContainingIgnoreCase(String letters);
    List<Book> findBookByAuthorLastNameStartWith(String letters);
    int countBooksByTitleIsGreaterThan(String title);
    int countCopiesByAuthorOrderByCopiesDesc(Author author);
    BookDTO findBookByTitle(String title);
}
