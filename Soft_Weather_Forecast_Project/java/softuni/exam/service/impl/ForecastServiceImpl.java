package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ForecastRootSeedDto;
import softuni.exam.models.dto.xml.ForecastSeedDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DayOfWeek;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final CityService cityService;
    private final static String FORECASTS_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";

    private final static String INVALID_FORECAST_DATA = "Invalid forecast\n";
    private final static String SUCCESSFULLY_FORECAST_INSERT = "Successfully import forecast %s - %.2f\n";

    private final ModelMapper mapper;
    private final Gson gson;
    private final ValidationUtil validator;


    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, CityService cityService, ModelMapper mapper, Gson gson, ValidationUtil validator) {
        this.forecastRepository = forecastRepository;
        this.cityService = cityService;
        this.mapper = mapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(ForecastRootSeedDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        ForecastRootSeedDto forecastRootSeedDtos = (ForecastRootSeedDto) unmarshaller.unmarshal(new File(FORECASTS_FILE_PATH));

        List<ForecastSeedDto> forecastSeedDtos = forecastRootSeedDtos.getForecasts();

        for (ForecastSeedDto forecastSeedDto : forecastSeedDtos) {

            if (forecastSeedDto.getDayOfWeek().equals("NULL")) {
                sb.append(INVALID_FORECAST_DATA);
                continue;
            }

            boolean isForecastDataValid = validator.isValid(forecastSeedDto);

            if (!isForecastDataValid) {
                sb.append(INVALID_FORECAST_DATA);
                continue;
            }

            boolean isForecastExist = isForecastForTheCityAlreadyExist(DayOfWeek.valueOf(forecastSeedDto.getDayOfWeek()), forecastSeedDto.getCity());

            if (isForecastExist) {
                sb.append(INVALID_FORECAST_DATA);
                continue;
            }

            boolean isCityExist = cityService.findCityById(forecastSeedDto.getCity()).isPresent();
            if (!isCityExist) {
                sb.append(INVALID_FORECAST_DATA);
                continue;
            }

            City city = cityService.findCityById(forecastSeedDto.getCity()).get();
            Forecast forecastToSave = mapper.map(forecastSeedDto, Forecast.class);

            forecastToSave.setCity(city);
            saveValidForecast(forecastToSave);

            sb.append(String.format(SUCCESSFULLY_FORECAST_INSERT, forecastToSave.getDayOfWeek(), forecastToSave.getMaxTemperature()));
        }

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();

        List<Forecast> forecasts = findForecastsByDayOfWeekAndCityPopulationLessThan(DayOfWeek.SUNDAY, 150000);

        forecasts.forEach(sb::append);

        return sb.toString().trim();
    }

    @Override
    public void saveValidForecast(Forecast forecast) {
        this.forecastRepository.saveAndFlush(forecast);
    }

    @Override
    public boolean isForecastForTheCityAlreadyExist(DayOfWeek dayOfWeek, Long id) {
        return this.forecastRepository.findForecastByDayOfWeekAndCityId(dayOfWeek, id).isPresent();
    }

    @Override
    public List<Forecast> findForecastsByDayOfWeekAndCityPopulationLessThan(DayOfWeek dayOfWeek, int population) {
        return this.forecastRepository.findForecastsByDayOfWeekAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(dayOfWeek, population);
    }
}
