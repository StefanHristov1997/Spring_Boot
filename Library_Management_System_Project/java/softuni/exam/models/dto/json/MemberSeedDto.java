package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSeedDto {

    @Size(min = 2, max = 30)
    @NotNull
    @Expose
    private String firstName;

    @Size(min = 2, max = 30)
    @NotNull
    @Expose
    private String lastName;

    @Size(min = 2, max = 40)
    @Expose
    private String address;

    @Size(min = 2, max = 20)
    @NotNull @Expose
    private String phoneNumber;
}
