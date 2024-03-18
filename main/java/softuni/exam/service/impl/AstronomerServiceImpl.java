package softuni.exam.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmlDTOs.AstronomerRootDto;
import softuni.exam.models.dto.xmlDTOs.AstronomerSeedDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.constans.FilePaths.ASTRONOMERS_FILE_PATH;
import static softuni.exam.constans.Messages.INVALID_ASTRONOMER;
import static softuni.exam.constans.Messages.SUCCESSFULLY_IMPORT_ASTRONOMER;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private final AstronomerRepository astronomerRepository;

    private final StarService starService;
    private final ValidationUtil validator;
    private final ModelMapper mapper;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarService starService, ValidationUtil validator, ModelMapper mapper) {
        this.astronomerRepository = astronomerRepository;
        this.starService = starService;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRONOMERS_FILE_PATH));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(AstronomerRootDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File(ASTRONOMERS_FILE_PATH);

        AstronomerRootDto astronomerRootDTOs = (AstronomerRootDto) unmarshaller.unmarshal(file);
        astronomerRootDTOs.getAstronomers()
                .forEach(astronomerSeedDto -> {

                    boolean isAstronomerDtoValid = this.validator.isValid(astronomerSeedDto);

                    Star star = starService.findStarById(astronomerSeedDto.getObservingStar());

                    if(isAstronomerDtoValid){
                        if(star == null){
                            isAstronomerDtoValid = false;
                        }else {
                            Astronomer astronomerExist = astronomerRepository
                                    .findAstronomerByFirstNameAndLastName
                                            (astronomerSeedDto.getFirstName(), astronomerSeedDto.getLastName());

                            if (astronomerExist != null) {
                                isAstronomerDtoValid = false;
                            }else {
                                Astronomer astronomerToSave = mapper.map(astronomerSeedDto, Astronomer.class);

                                star.getObservers().add(astronomerToSave);
                                starService.saveUpdatedStar(star);

                                astronomerToSave.setObservingStar(star);


                                astronomerRepository.saveAndFlush(astronomerToSave);
                            }
                        }

                    }
                    sb.append(isAstronomerDtoValid ? String.format(SUCCESSFULLY_IMPORT_ASTRONOMER, getAstronomerFullName(astronomerSeedDto), astronomerSeedDto.getAverageObservationHours())
                            : INVALID_ASTRONOMER);
                });

        return sb.toString();
    }


    @Override
    public String getAstronomerFullName(AstronomerSeedDto astronomer) {
        return astronomer.getFirstName() + " " + astronomer.getLastName();
    }
}
