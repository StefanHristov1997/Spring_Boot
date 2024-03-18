package softuni.exam.models.dto.xmlDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
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
@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDto {

    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @XmlElement(name = "salary")
    @DecimalMin(value = "15000")
    @NotNull
    private Double salary;

    @XmlElement(name = "average_observation_hours")
    @DecimalMin(value = "500")
    @NotNull
    private Double averageObservationHours;

    @XmlElement(name = "birthday")
    private String birthday;

    @XmlElement(name = "observing_star_id")
    private Long observingStar;
}
