package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonSeedDto {

    @Expose
    @Size(min = 2, max = 30)
    private String firstName;

    @Expose
    @Size(min = 2, max = 30)
    private String lastName;

    @Expose
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$")
    private String email;

    @Expose
    @Size(min = 2, max = 30)
    private String phone;

    @Expose
    private String statusType;

    @Expose
    private Long country;
}
