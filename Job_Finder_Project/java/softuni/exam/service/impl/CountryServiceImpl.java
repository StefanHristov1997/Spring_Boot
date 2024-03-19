package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final static String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<CountrySeedDto> countriesSeedDTOs = Arrays.stream(gson.fromJson(readCountriesFileContent(), CountrySeedDto[].class)).toList();

        for (CountrySeedDto countriesSeedDTO : countriesSeedDTOs) {
            boolean isCountryDataValid = validationUtil.isValid(countriesSeedDTO);

            if (!isCountryDataValid) {
                sb.append("Invalid country\n");
                continue;
            }

            Country countryToSave = mapper.map(countriesSeedDTO, Country.class);
            saveValidCountry(countryToSave);

            sb.append(String.format("Successfully imported country %s - %s\n", countryToSave.getName(), countryToSave.getCurrency()));
        }

        return sb.toString();

    }

    @Override
    public void saveValidCountry(Country country) {
        this.countryRepository.saveAndFlush(country);
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        return this.countryRepository.findById(id);
    }
}
