package softuni.exam.service;

import softuni.exam.models.entity.Constellation;

import java.io.IOException;

public interface ConstellationService {

    boolean areImported();

    String readConstellationsFromFile() throws IOException;

    String importConstellations() throws IOException;

    Constellation getConstellationById(Long id);

    void saveConstellation(Constellation constellation);
}
