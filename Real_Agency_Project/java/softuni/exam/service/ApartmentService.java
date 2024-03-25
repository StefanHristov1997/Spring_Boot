package softuni.exam.service;

import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;


public interface ApartmentService {
    
    boolean areImported();

    String readApartmentsFromFile() throws IOException;

    String importApartments() throws IOException, JAXBException;

    void saveValidApartment(Apartment apartment);

    Optional<Apartment> findApartmentByTownAndArea(Town town, double area);

    Apartment findApartmentById(Long id);
}
