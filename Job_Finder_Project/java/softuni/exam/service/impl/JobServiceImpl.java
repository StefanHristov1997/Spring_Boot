package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.JobRootSeedDto;
import softuni.exam.models.dto.xml.JobSeedDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final static String JOBS_FILE_PATH = "src/main/resources/files/xml/jobs.xml";

    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;
    private final CompanyService companyService;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, ValidationUtil validationUtil, ModelMapper mapper, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
        this.companyService = companyService;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOBS_FILE_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext context = JAXBContext.newInstance(JobRootSeedDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        JobRootSeedDto jobsSeedDto = (JobRootSeedDto) unmarshaller.unmarshal(new File(JOBS_FILE_PATH));

        List<JobSeedDto> jobSeedDtos = jobsSeedDto.getJobs();

        for (JobSeedDto jobSeedDto : jobSeedDtos) {
            boolean isCompanyExist = companyService.findCompanyById(jobSeedDto.getCompany()).isPresent();
            if (!isCompanyExist) {
                sb.append("Invalid job\n");
                continue;
            }

            boolean isJobDataValid = validationUtil.isValid(jobSeedDto);

            if (!isJobDataValid) {
                sb.append("Invalid job\n");
                continue;
            }

            Job jobToSave = mapper.map(jobSeedDto, Job.class);
            Company company = companyService.findCompanyById(jobSeedDto.getCompany()).get();
            jobToSave.setCompany(company);

            saveValidJob(jobToSave);
            sb.append(String.format("Successfully imported job %s\n", jobToSave.getTitle()));
        }

        return sb.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder sb = new StringBuilder();

        List<Job> bestJobs = jobRepository.findDistinctBySalaryGreaterThanEqualAndHoursAWeekLessThanEqualOrderBySalaryDesc(5000, 30);
        List<String> jobTitles = new ArrayList<>();

        for (Job bestJob : bestJobs) {
            if (jobTitles.contains(bestJob.getTitle())) {
                continue;
            }
            jobTitles.add(bestJob.getTitle());
            sb.append(bestJob);
        }

        return sb.toString();
    }

    @Override
    public void saveValidJob(Job job) {
        this.jobRepository.saveAndFlush(job);
    }
}
