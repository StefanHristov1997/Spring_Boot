package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.TownSeedDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;

    private final static String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final static String SUCCESSFULLY_IMPORT_TOWN = "Successfully imported town %s - %d\n";
    private final static String INVALID_TOWN = "Invalid town\n";

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper mapper, ValidationUtil validator) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<TownSeedDTO> townSeedDTOS = Arrays.stream(gson.fromJson(readTownsFileContent(), TownSeedDTO[].class)).toList();

        for (TownSeedDTO townSeedDTO : townSeedDTOS) {
            boolean isTownDataValid = validator.isValid(townSeedDTO);

            if (!isTownDataValid) {
                sb.append(INVALID_TOWN);
                continue;
            }

            Town townToSave = mapper.map(townSeedDTO, Town.class);
            saveValidTown(townToSave);

            sb.append(String.format(SUCCESSFULLY_IMPORT_TOWN, townToSave.getTownName(), townToSave.getPopulation()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidTown(Town town) {
        this.townRepository.saveAndFlush(town);
    }

    @Override
    public Optional<Town> findTownByTownName(String townName) {
        return this.townRepository.findTownByTownName(townName);
    }
}
