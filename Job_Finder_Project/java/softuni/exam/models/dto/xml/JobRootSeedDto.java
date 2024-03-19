package softuni.exam.models.dto.xml;
///<jobs>
//    <job>
//        <title>Assistant Professor</title>
//        <hoursAWeek>21.1</hoursAWeek>
//        <salary>5754.07</salary>
//        <description>Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna,
//            ac consequat metus sapien ut nunc.
//        </description>
//        <companyId>38</companyId>
//    </job>

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobRootSeedDto {

    @XmlElement(name = "job")
    private List<JobSeedDto> jobs;
}
