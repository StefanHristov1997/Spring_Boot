package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.CompanyRootSeedDto;
import softuni.exam.models.dto.xml.CompanySeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final static String COMPANIES_FILE_PATH = "src/main/resources/files/xml/companies.xml";

    private final ModelMapper mapper;
    private final CountryService countryService;
    private final ValidationUtil validationUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper, CountryService countryService, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.countryService = countryService;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_FILE_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(CompanyRootSeedDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        CompanyRootSeedDto companiesRootDto = (CompanyRootSeedDto) unmarshaller.unmarshal(new File(COMPANIES_FILE_PATH));

        List<CompanySeedDto> companySeedDtos = companiesRootDto.getCompanies();

        for (CompanySeedDto companySeedDto : companySeedDtos) {
            boolean isCompanyAlreadyExist = findCompanyByName(companySeedDto.getName()).isPresent();

            if (isCompanyAlreadyExist) {
                sb.append("Invalid company\n");
                continue;
            }

            boolean isCountryExist = countryService.findCountryById(companySeedDto.getCountry()).isPresent();

            if (!isCountryExist) {
                sb.append("Invalid company\n");
                continue;
            }

            boolean isCompanyDataValid = validationUtil.isValid(companySeedDto);

            if (!isCompanyDataValid) {
                sb.append("Invalid company\n");
                continue;
            }

            Country country = countryService.findCountryById(companySeedDto.getCountry()).get();
            Company companyToSave = mapper.map(companySeedDto, Company.class);

            companyToSave.setCountry(country);
            saveValidCompany(companyToSave);

            sb.append(String.format("Successfully imported company %s - %d\n", companyToSave.getName(), companyToSave.getCountry().getId()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidCompany(Company company) {
        this.companyRepository.saveAndFlush(company);
    }

    @Override
    public Optional<Company> findCompanyByName(String name) {
        return this.companyRepository.findCompanyByName(name);
    }

    @Override
    public Optional<Company> findCompanyById(Long id) {
        return this.companyRepository.findCompanyById(id);
    }
}
