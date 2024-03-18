package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsonDTOs.StarSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.enums.StarType;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constans.FilePaths.STARS_FILE_PATH;
import static softuni.exam.constans.Messages.INCORRECT_STAR_DATA;
import static softuni.exam.constans.Messages.SUCCESSFULLY_IMPORT_STAR;

@Service
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;

    private final ConstellationService constellationService;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validator;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ConstellationService constellationService, Gson gson, ModelMapper mapper, ValidationUtil validator) {
        this.starRepository = starRepository;
        this.constellationService = constellationService;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Path.of(STARS_FILE_PATH));
    }

    @Override
    public String importStars() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readStarsFileContent(), StarSeedDto[].class))
                .filter(starSeedDto -> {
                    boolean isStarSeedDtoValid = validator.isValid(starSeedDto);

                    boolean isPresent = starRepository.findStarByName(starSeedDto.getName()).isPresent();

                    if (isPresent) {
                        return isStarSeedDtoValid = false;
                    }

                    sb.append(isStarSeedDtoValid ? String.format(SUCCESSFULLY_IMPORT_STAR, starSeedDto.getName(), starSeedDto.getLightYears())
                            : INCORRECT_STAR_DATA);

                    return isStarSeedDtoValid;
                }).map(starSeedDto -> {
                    Star starToSave = mapper.map(starSeedDto, Star.class);

                    Constellation constellation = constellationService.getConstellationById(starSeedDto.getConstellation());
                    constellation.getStars().add(starToSave);
                    constellationService.saveConstellation(constellation);

                    starToSave.setConstellation(constellation);
                    return starToSave;
                }).forEach(starRepository::save);

        return sb.toString();
    }

    @Override
    public String exportStars() {
        List<Star> nonObservedStars = getNonObservedStars();
        StringBuilder sb = new StringBuilder();
        nonObservedStars.forEach(sb::append);
        return sb.toString().trim();
    }

    @Override
    public Star findStarById(Long id) {
        return this.starRepository.findStarById(id);
    }

    @Override
    public void saveUpdatedStar(Star star) {
        this.starRepository.saveAndFlush(star);
    }

    @Override
    public List<Star> getNonObservedStars() {
        return this.starRepository.findStarsByStarTypeAndObserversIsEmptyOrderByLightYears(StarType.RED_GIANT);
    }
}
