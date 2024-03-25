package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.AgentSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;

    private final static String AGENTS_FILE_PATH = "src/main/resources/files/json/agents.json";
    private final static String SUCCESSFULLY_IMPORT_AGENT = "Successfully imported agent - %s %s\n";
    private final static String INVALID_AGENT = "Invalid agent\n";

    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validator;
    private final TownService townService;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, Gson gson, ModelMapper mapper, ValidationUtil validator, TownService townService) {
        this.agentRepository = agentRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENTS_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        List<AgentSeedDTO> agentSeedDTOS = Arrays.stream(gson.fromJson(readAgentsFromFile(), AgentSeedDTO[].class)).toList();

        for (AgentSeedDTO agentSeedDTO : agentSeedDTOS) {
            boolean isAgentAlreadyExist = findAgentByFirstName(agentSeedDTO.getFirstName()).isPresent();

            if (isAgentAlreadyExist) {
                sb.append(INVALID_AGENT);
                continue;
            }

            boolean isAgentDataValid = validator.isValid(agentSeedDTO);

            if (!isAgentDataValid) {
                sb.append(INVALID_AGENT);
                continue;
            }

            boolean isTownExist = townService.findTownByTownName(agentSeedDTO.getTown()).isPresent();

            if (!isTownExist) {
                sb.append(INVALID_AGENT);
                continue;
            }

            Agent agentToSave = mapper.map(agentSeedDTO, Agent.class);
            Town town = townService.findTownByTownName(agentSeedDTO.getTown()).get();
            agentToSave.setTown(town);
            saveValidAgent(agentToSave);

            sb.append(String.format(SUCCESSFULLY_IMPORT_AGENT, agentToSave.getFirstName(), agentToSave.getLastName()));
        }

        return sb.toString();
    }

    @Override
    public void saveValidAgent(Agent agent) {
        this.agentRepository.saveAndFlush(agent);
    }

    @Override
    public Optional<Agent> findAgentByFirstName(String firstName) {
        return this.agentRepository.findAgentByFirstName(firstName);
    }
}
