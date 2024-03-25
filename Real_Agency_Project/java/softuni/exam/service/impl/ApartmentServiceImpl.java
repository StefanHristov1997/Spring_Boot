package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ApartmentRootDTO;
import softuni.exam.models.dto.xml.ApartmentSeedDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;

    private final static String APARTMENTS_FILE_PATH = "src/main/resources/files/xml/apartments.xml";
    private final static String SUCCESSFULLY_IMPORT_APARTMENT = "Successfully imported apartment %s - %.2f\n";
    private final static String INVALID_APARTMENT = "Invalid apartment\n";

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final TownService townService;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, Gson gson, ModelMapper mapper, ValidationUtil validator, TownService townService) {
        this.apartmentRepository = apartmentRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENTS_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(ApartmentRootDTO.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        ApartmentRootDTO apartmentRootDTO = (ApartmentRootDTO) unmarshaller.unmarshal(new File(APARTMENTS_FILE_PATH));

        List<ApartmentSeedDTO> apartmentSeedDTOS = apartmentRootDTO.getApartments();

        for (ApartmentSeedDTO apartmentSeedDTO : apartmentSeedDTOS) {
            Town town = townService.findTownByTownName(apartmentSeedDTO.getTown()).get();
            boolean isApartmentAlreadyExist = findApartmentByTownAndArea(town, apartmentSeedDTO.getArea()).isPresent();

            if (isApartmentAlreadyExist) {
                sb.append(INVALID_APARTMENT);
                continue;
            }

            boolean isApartmentDataValid = validator.isValid(apartmentSeedDTO);

            if (!isApartmentDataValid) {
                sb.append(INVALID_APARTMENT);
                continue;
            }

            Apartment apartmentToSave = mapper.map(apartmentSeedDTO, Apartment.class);
            apartmentToSave.setTown(town);
            saveValidApartment(apartmentToSave);

            sb.append(String.format(SUCCESSFULLY_IMPORT_APARTMENT, apartmentToSave.getApartmentType(), apartmentToSave.getArea()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidApartment(Apartment apartment) {
        this.apartmentRepository.saveAndFlush(apartment);
    }

    @Override
    public Optional<Apartment> findApartmentByTownAndArea(Town town, double area) {
        return this.apartmentRepository.findApartmentByTownAndArea(town, area);
    }

    @Override
    public Apartment findApartmentById(Long id) {
        return this.apartmentRepository.findApartmentById(id);
    }
}
