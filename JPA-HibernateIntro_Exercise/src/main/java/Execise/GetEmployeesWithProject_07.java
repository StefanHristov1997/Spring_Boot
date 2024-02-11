package Execise;

import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.Scanner;

public class GetEmployeesWithProject_07 {
    private final static String GET_EMPLOYEE_BY_ID = "SELECT e FROM Employee AS e" +
            " WHERE e.id = :id";

    private final static String PRINT_FORMAT = "%s %s - %s\n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        final int employee_id = new Scanner(System.in).nextInt();

        final Employee employee = getEmployeeByID(entityManager, employee_id);
        printResult(employee);
        entityManager.close();
    }

    private static Employee getEmployeeByID(EntityManager entityManager, int employee_id) {
        return entityManager.createQuery(GET_EMPLOYEE_BY_ID, Employee.class).setParameter("id", employee_id).getSingleResult();
    }

    private static void printResult(Employee employee){
        System.out.printf(PRINT_FORMAT, employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> System.out.println(project.getName()));

    }
}
