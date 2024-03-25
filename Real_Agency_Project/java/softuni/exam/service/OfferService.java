package softuni.exam.service;

import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


public interface OfferService {

    boolean areImported();

    String readOffersFileContent() throws IOException;
	
	String importOffers() throws IOException, JAXBException;

    String exportOffers();

    void saveValidOffer(Offer offer);

    List<Offer> findBestOffers(ApartmentType apartmentType);
}
