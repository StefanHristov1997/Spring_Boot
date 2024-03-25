package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TownSeedDTO {

    @Expose
    @Size(min = 2)
    private String townName;

    @Expose
    @Positive
    private int population;
}
