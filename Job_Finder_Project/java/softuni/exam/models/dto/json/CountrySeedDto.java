package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountrySeedDto {

    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    @Size(min = 2, max = 19)
    private String countryCode;

    @Expose
    @Size(min = 2, max = 19)
    private String currency;
}
