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
    private final static String INVALID_COUNTRY_DATA = "Invalid country\n";
    private final static String SUCCESSFULLY_COUNTRY_INSERT = "Successfully imported country %s - %s\n";

    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validator;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper mapper, Gson gson, ValidationUtil validator) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<CountrySeedDto> countriesSeedDtos = Arrays.stream(gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class)).toList();

        for (CountrySeedDto countrySeedDto : countriesSeedDtos) {
            boolean isCountrySeedDtoValid = validator.isValid(countrySeedDto);

            if (!isCountrySeedDtoValid) {
                sb.append(INVALID_COUNTRY_DATA);
                continue;
            }

            if (isCountryAlreadyExist(countrySeedDto.getCountryName())) {
                sb.append(INVALID_COUNTRY_DATA);
                continue;
            }

            Country countryToSave = mapper.map(countrySeedDto, Country.class);
            saveValidCountry(countryToSave);
            sb.append(String.format(SUCCESSFULLY_COUNTRY_INSERT, countryToSave.getCountryName(), countryToSave.getCurrency()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidCountry(Country country) {
        this.countryRepository.saveAndFlush(country);
    }

    @Override
    public boolean isCountryAlreadyExist(String countryName) {
        return this.countryRepository.findCountryByCountryName(countryName).isPresent();
    }

    @Override
    public Optional<Country> findCountryById(Long id) {
        return this.countryRepository.findCountryById(id);
    }


}
