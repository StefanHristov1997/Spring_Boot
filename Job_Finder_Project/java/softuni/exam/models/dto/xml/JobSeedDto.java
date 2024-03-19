package softuni.exam.models.dto.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobSeedDto {

    @XmlElement
    @Size(min = 2, max = 40)
    private String jobTitle;

    @XmlElement
    @Min(300)
    private BigDecimal salary;

    @XmlElement
    @Min(10)
    private BigDecimal hoursAWeek;

    @XmlElement
    @Size(min = 5)
    private String description;

    @XmlElement(name = "companyId")
    private Long company;
}
