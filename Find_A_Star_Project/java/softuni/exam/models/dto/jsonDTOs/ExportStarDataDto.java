package softuni.exam.models.dto.jsonDTOs;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportStarDataDto {

    @Expose
    private String name;

    @Expose
    private Double lightYears;

    @Expose
    private String description;

    @Expose
    private String constellationName;

    @Override
    public String toString() {
//    "Star: {starName}
//"   *Distance: {lightYears} light years
//"   **Description: {description}
//"   ***Constellation: {constellationName}
//. . ."
        return  "Star: " + getName() + System.lineSeparator() +
                "   *Distance: " + String.format("%.2f light years", getLightYears()) + System.lineSeparator() +
                "   **Description: " + getDescription() + System.lineSeparator() +
                "   ***Constellation: " + getConstellationName() + System.lineSeparator();
    }
}
