package softuni.exam.service;


import softuni.exam.models.entity.Country;

import java.io.IOException;
import java.util.Optional;


public interface CountryService {

    boolean areImported();

    String readCountriesFromFile() throws IOException;

    String importCountries() throws IOException;

    void saveValidCountry(Country country);

    boolean isCountryAlreadyExist(String countryName);

    Optional<Country> findCountryById(Long id);
}
