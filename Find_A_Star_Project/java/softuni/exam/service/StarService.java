package softuni.exam.service;

import softuni.exam.models.dto.jsonDTOs.ExportStarDataDto;
import softuni.exam.models.entity.Star;

import java.io.IOException;
import java.util.List;


public interface StarService {

    boolean areImported();

    String readStarsFileContent() throws IOException;
	
	String importStars() throws IOException;

    String exportStars();

    Star findStarById(Long id);
    void saveUpdatedStar(Star star);

    List<Star> getNonObservedStars();
}
