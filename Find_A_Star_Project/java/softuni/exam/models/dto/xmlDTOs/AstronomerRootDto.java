package softuni.exam.models.dto.xmlDTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerRootDto {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedDto> astronomers;

}


//<astronomers>
//    <astronomer>
//        <average_observation_hours>176858.79</average_observation_hours>
//        <birthday>1989-01-01</birthday>
//        <first_name>Drake</first_name>
//        <last_name>Hawthorne</last_name>
//        <salary>207615.71</salary>
//        <observing_star_id>50</observing_star_id>
//    </astronomer>