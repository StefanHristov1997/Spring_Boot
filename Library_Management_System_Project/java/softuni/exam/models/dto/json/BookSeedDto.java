package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.Genre;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSeedDto {

    @Size(min = 3, max = 40)
    @NotNull
    @Expose
    private String title;

    @Size(min = 3, max = 40)
    @NotNull
    @Expose
    private String author;

    @Size(min = 5)
    @NotNull
    @Expose
    private String description;

    @NotNull
    @Expose
    private Boolean available;

    @NotNull
    @Expose
    private String genre;

    @Positive
    @NotNull
    @Expose
    private Double rating;
}
