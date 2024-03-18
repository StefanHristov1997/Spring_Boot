package softuni.exam.service;

import softuni.exam.models.dto.xmlDTOs.AstronomerSeedDto;
import softuni.exam.models.entity.Astronomer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;


public interface AstronomerService {

    boolean areImported();

    String readAstronomersFromFile() throws IOException;

	String importAstronomers() throws IOException, JAXBException;

    String getAstronomerFullName(AstronomerSeedDto astronomer);

}
