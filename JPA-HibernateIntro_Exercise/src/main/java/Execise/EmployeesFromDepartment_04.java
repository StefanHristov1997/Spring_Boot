package Execise;

import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment_04 {
    private final static String FILTER_EMPLOYEES_BY_DEPARTMENT_NAME = "SELECT e FROM Employee AS e" +
            "   WHERE e.department.name = :dp" +
            "   ORDER BY e.salary, e.id";

    private final static String RESEARCH_AND_DEVELOPMENT_DEPARTMENT = "Research and Development";
    private final static String PRINT_FORMAT = "%s %s from %s - $%.2f\n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        entityManager.createQuery(FILTER_EMPLOYEES_BY_DEPARTMENT_NAME, Employee.class).setParameter("dp",RESEARCH_AND_DEVELOPMENT_DEPARTMENT)
                .getResultList()
                .forEach(employee -> System.out.printf(PRINT_FORMAT,
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getDepartment().getName(), employee.getSalary()));

    }
}
