package Execise;

import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class FindEmployeesByFirstName_10 {
    private final static String GET_EMPLOYEES_BY_FIRST_NAME_PATTERN = "SELECT e FROM Employee AS e WHERE SUBSTRING(e.firstName,1) LIKE(:pt)";
    private final static String PRINT_FORMAT = "%s %s - %s - ($%.2f)\n";
    public static void main(String[] args) {

        EntityManager entityManager = Utils.creatEntityManger();

        String inputPattern = new Scanner(System.in).nextLine() + "%";

        entityManager.createQuery(GET_EMPLOYEES_BY_FIRST_NAME_PATTERN, Employee.class)
                .setParameter("pt", inputPattern)
                .getResultList()
                .forEach(employee -> System.out.printf(PRINT_FORMAT,
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary()));

        entityManager.close();
    }
}
