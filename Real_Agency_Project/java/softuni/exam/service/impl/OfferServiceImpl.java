package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.OfferRootDTO;
import softuni.exam.models.dto.xml.OfferSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
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
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    private final static String OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private final static String SUCCESSFULLY_IMPORT_OFFER = "Successfully imported offer %.2f\n";
    private final static String INVALID_OFFER = "Invalid offer\n";

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final ApartmentService apartmentService;
    private final AgentService agentService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, Gson gson, ModelMapper mapper, ValidationUtil validator, ApartmentService apartmentService, AgentService agentService) {
        this.offerRepository = offerRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
        this.apartmentService = apartmentService;
        this.agentService = agentService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(OfferRootDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OfferRootDTO offerDTOs = (OfferRootDTO) unmarshaller.unmarshal(new File(OFFERS_FILE_PATH));

        List<OfferSeedDTO> offerSeedDTOS = offerDTOs.getOffers();

        for (OfferSeedDTO offerSeedDTO : offerSeedDTOS) {
            boolean isAgentExist = agentService.findAgentByFirstName(offerSeedDTO.getAgent().getName()).isPresent();

            if (!isAgentExist) {
                sb.append(INVALID_OFFER);
                continue;
            }

            boolean isOfferDataValid = validator.isValid(offerSeedDTO);

            if (!isOfferDataValid) {
                sb.append(INVALID_OFFER);
                continue;
            }

            Agent agent = agentService.findAgentByFirstName(offerSeedDTO.getAgent().getName()).get();
            Apartment apartment = apartmentService.findApartmentById(offerSeedDTO.getApartment().getId());

            Offer offerToSave = mapper.map(offerSeedDTO, Offer.class);

            offerToSave.setAgent(agent);
            offerToSave.setApartment(apartment);

            saveValidOffer(offerToSave);

            sb.append(String.format(SUCCESSFULLY_IMPORT_OFFER, offerToSave.getPrice()));
        }

        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        List<Offer> bestOffers = findBestOffers(ApartmentType.three_rooms);

        bestOffers.forEach(sb::append);

        return sb.toString().trim();
    }

    @Override
    public void saveValidOffer(Offer offer) {
        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public List<Offer> findBestOffers(ApartmentType apartmentType) {
        return this.offerRepository.findBestOffers(apartmentType);
    }
}
