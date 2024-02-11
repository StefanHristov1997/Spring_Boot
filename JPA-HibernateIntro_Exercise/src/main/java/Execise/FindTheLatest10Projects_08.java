package Execise;

import entities.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class FindTheLatest10Projects_08 {
    private final static String GET_STARTED_PROJECTS = "SELECT p FROM Project AS p WHERE p.endDate IS NULL" +
            " ORDER BY p.name";
    private final static String PRINT_FORMAT = "Project name: %s\nProject Description: %s\nProject Start Date: %s\nProject End Date: %s\n";

    public static void main(String[] args) {
        EntityManager entityManager = Utils.creatEntityManger();

        List<Project> startedProjects = entityManager.createQuery(GET_STARTED_PROJECTS, Project.class).setMaxResults(10).getResultList();

        startedProjects.forEach(project ->
                System.out.printf(PRINT_FORMAT,
                        project.getName(),
                        project.getDescription(),
                        project.getStartDate(),
                        project.getEndDate()));

        entityManager.close();

    }
}
