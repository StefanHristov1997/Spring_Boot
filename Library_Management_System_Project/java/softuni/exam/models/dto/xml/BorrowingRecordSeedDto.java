package softuni.exam.models.dto.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "borrowing_record")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordSeedDto {

    @XmlElement (name = "borrow_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private String borrowDate;

    @XmlElement(name = "return_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private String returnDate;

    @XmlElement(name = "book")
    @NotNull
    private BookTitleDto book;

    @XmlElement(name = "member")
    @NotNull
    private MemberIdDto member;

    @Size(min = 3, max = 100)
    private String remarks;
}
