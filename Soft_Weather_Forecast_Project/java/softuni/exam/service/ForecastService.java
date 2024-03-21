package softuni.exam.service;

import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DayOfWeek;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


public interface ForecastService {

    boolean areImported();

    String readForecastsFromFile() throws IOException;
	
	String importForecasts() throws IOException, JAXBException;

    String exportForecasts();

    void saveValidForecast(Forecast forecast);

    boolean isForecastForTheCityAlreadyExist(DayOfWeek dayOfWeek, Long id);

    List<Forecast> findForecastsByDayOfWeekAndCityPopulationLessThan(DayOfWeek dayOfWeek, int population);
}
