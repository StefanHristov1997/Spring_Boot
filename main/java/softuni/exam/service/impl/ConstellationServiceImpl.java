package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDTOs.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constans.FilePaths.CONSTELLATIONS_FILE_PATH;
import static softuni.exam.constans.Messages.INVALID_CONSTELLATION;
import static softuni.exam.constans.Messages.SUCCESSFULLY_IMPORT_CONSTELLATION;

@Service
public class ConstellationServiceImpl implements ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validator;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper mapper, Gson gson, ValidationUtil validator) {
        this.constellationRepository = constellationRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        final StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class))
                .filter(constellationSeedDto -> {
                    boolean isValidDto = validator.isValid(constellationSeedDto);

                    boolean isPresent =
                            constellationRepository.findConstellationByName(constellationSeedDto.getName()).isPresent();

                    if (isPresent) {
                        return isValidDto = false;
                    }

                    sb.append(isValidDto ?
                            String.format(SUCCESSFULLY_IMPORT_CONSTELLATION,
                                    constellationSeedDto.getName(), constellationSeedDto.getDescription())
                            : INVALID_CONSTELLATION);
                    return isValidDto;
                }).map(constellationSeedDto -> mapper.map(constellationSeedDto, Constellation.class))
                .forEach(constellationRepository::saveAndFlush);

        return sb.toString();
    }

    @Override
    public Constellation getConstellationById(Long id) {
        return this.constellationRepository.findConstellationById(id).get();
    }

    @Override
    public void saveConstellation(Constellation constellation) {
        this.constellationRepository.saveAndFlush(constellation);
    }
}
