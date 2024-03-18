package softuni.exam.models.dto.jsonDTOs;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class ConstellationSeedDto implements Serializable {

    @Expose
    @Size(min = 3, max = 20)
    private String name;


    @Expose
    @Size(min = 5)
    private String description;

}
