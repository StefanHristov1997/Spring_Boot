package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PersonSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.CountryService;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final static String PEOPLE_FILE_PATH = "src/main/resources/files/json/people.json";

    private final Gson gson;
    private final ModelMapper mapper;
    private final CountryService countryService;
    private final ValidationUtil validationUtil;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, Gson gson, ModelMapper mapper, CountryService countryService, ValidationUtil validationUtil) {
        this.personRepository = personRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.countryService = countryService;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PEOPLE_FILE_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        List<PersonSeedDto> peopleSeedDTOs = Arrays.stream(gson.fromJson(readPeopleFromFile(), PersonSeedDto[].class)).toList();

        for (PersonSeedDto personSeedDto : peopleSeedDTOs) {
            boolean isPersonAlreadyExist = findPersonByFirstNameEmailAndPhone(personSeedDto.getFirstName(), personSeedDto.getEmail(), personSeedDto.getPhone()).isPresent();

            if (isPersonAlreadyExist) {
                sb.append("Invalid person\n");
                continue;
            }

            boolean isCountryExist = countryService.findCountryById(personSeedDto.getCountry()).isPresent();

            if (!isCountryExist) {
                sb.append("Invalid person\n");
                continue;
            }

            boolean isPersonDataValid = validationUtil.isValid(personSeedDto);
            if (!isPersonDataValid) {
                sb.append("Invalid person\n");
                continue;
            }

            Country country = countryService.findCountryById(personSeedDto.getCountry()).get();
            Person personToSave = mapper.map(personSeedDto, Person.class);
            personToSave.setCountry(country);

            saveValidPerson(personToSave);

            sb.append(String.format("Successfully imported person %s %s\n", personToSave.getFirstName(), personToSave.getLastName()));
        }
        return sb.toString();
    }

    @Override
    public Optional<Person> findPersonByFirstNameEmailAndPhone(String firstName, String email, String phone) {
        return this.personRepository.findPersonByFirstNameOrEmailOrPhone(firstName, email, phone);
    }

    @Override
    public void saveValidPerson(Person person) {
        this.personRepository.saveAndFlush(person);
    }
}
