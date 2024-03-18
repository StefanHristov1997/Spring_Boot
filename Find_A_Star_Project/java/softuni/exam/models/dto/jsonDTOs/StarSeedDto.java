package softuni.exam.models.dto.jsonDTOs;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class StarSeedDto implements Serializable {

    @Expose
    @Size(min = 6)
    private String description;

    @Expose
    @Positive
    private double lightYears;

    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    private String starType;

    @Expose
    private long constellation;
}
