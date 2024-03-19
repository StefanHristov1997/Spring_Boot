package softuni.exam.service;

import softuni.exam.models.entity.Person;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

public interface PersonService {

    boolean areImported();

    String readPeopleFromFile() throws IOException;

    String importPeople() throws IOException, JAXBException;

    Optional<Person> findPersonByFirstNameEmailAndPhone(String firstName, String email, String phone);

    void saveValidPerson(Person person);
}
