package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends BaseEntity {

    @Column(name = "borrow_date", nullable = false, columnDefinition = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @Column(name = "return_date", nullable = false, columnDefinition = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @Column(nullable = true)
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private LibraryMember member;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //"Book title: {bookTitle}
        //"*Book author: {bookAuthor}
        //"**Date borrowed: {dateBorrowed}
        //"***Borrowed by: {firstName} {lastName}

        sb.append("Book title: ").append(book.getTitle()).append(System.lineSeparator());
        sb.append("*Book author: ").append(book.getAuthor()).append(System.lineSeparator());
        sb.append("**Date borrowed: ").append(getBorrowDate()).append(System.lineSeparator());
        sb.append("***Borrowed by: ").
                append(member.getFirstName()).append(" ").append(member.getLastName()).append(System.lineSeparator());

        return sb.toString();
    }
}
