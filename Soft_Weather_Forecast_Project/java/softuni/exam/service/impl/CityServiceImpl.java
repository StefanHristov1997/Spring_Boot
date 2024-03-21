package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.CitySeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryService countryService;
    private final static String CITIES_FILE_PATH = "src/main/resources/files/json/cities.json";

    private final static String INVALID_CITY_DATA = "Invalid city";
    private final static String SUCCESSFULLY_CITY_INSERT = "Successfully imported city %s - %d\n";

    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validator;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, ModelMapper mapper, Gson gson, ValidationUtil validator) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<CitySeedDto> citySeedDtos = Arrays.stream(gson.fromJson(readCitiesFileContent(), CitySeedDto[].class)).toList();

        for (CitySeedDto citySeedDto : citySeedDtos) {
            boolean isCityDataValid = validator.isValid(citySeedDto);

            if (!isCityDataValid) {
                sb.append(INVALID_CITY_DATA);
                continue;
            }

            if (isCityAlreadyExist(citySeedDto.getCityName())) {
                sb.append(INVALID_CITY_DATA);
                continue;
            }

            boolean isCountryExist = countryService.findCountryById(citySeedDto.getCountry()).isPresent();
            if (!isCountryExist) {
                sb.append(INVALID_CITY_DATA);
                continue;
            }

            Country country = countryService.findCountryById(citySeedDto.getCountry()).get();
            City cityToSave = mapper.map(citySeedDto, City.class);

            cityToSave.setCountry(country);
            saveValidCity(cityToSave);
            sb.append(String.format(SUCCESSFULLY_CITY_INSERT, cityToSave.getCityName(), cityToSave.getPopulation()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidCity(City city) {
        this.cityRepository.saveAndFlush(city);
    }

    @Override
    public boolean isCityAlreadyExist(String cityName) {
        return this.cityRepository.findCityByCityName(cityName).isPresent();
    }

    @Override
    public Optional<City> findCityById(Long id) {
        return this.cityRepository.findCityById(id);
    }
}
