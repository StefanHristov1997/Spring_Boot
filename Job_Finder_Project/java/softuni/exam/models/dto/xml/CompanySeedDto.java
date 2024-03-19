package softuni.exam.models.dto.xml;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//<companies>
//    <company>
//        <name>Heaney-Vandervort</name>
//        <dateEstablished>1981-10-06</dateEstablished>
//        <website>karley.biz</website>
//        <countryId>34</countryId>
//    </company>
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedDto {

    @XmlElement(name = "companyName")
    @Size(min = 2, max = 40)
    private String name;

    @XmlElement
    @Size(min = 2, max = 30)
    private String website;

    @XmlElement
    private String dateEstablished;

    @XmlElement(name = "countryId")
    private Long country;

}
