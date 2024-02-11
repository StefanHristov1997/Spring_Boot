package Execise;

import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class IncreaseSalaries_09 {
    private final static String GET_DEPARTMENT_IDs = "SELECT d.id FROM Department AS d " +
            " WHERE d.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')";
    private final static String UPDATE_EMPLOYEES_SALARY_BY_DEPARTMENT = "UPDATE Employee AS e" +
            "  SET e.salary = e.salary * 1.12" +
            "  WHERE e.department.id IN (:depsIds)";
    private final static String GET_EMPLOYEES_BY_DEPARTMENT = "SELECT e  FROM Employee AS e" +
            "  WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')";
    private final static String PRINT_FORMAT = "%s %s ($%.2f)\n";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        final List<Integer> departmentIds = entityManager.createQuery(GET_DEPARTMENT_IDs, Integer.class).getResultList();

        entityManager.getTransaction().begin();
        entityManager.createQuery(UPDATE_EMPLOYEES_SALARY_BY_DEPARTMENT).setParameter("depsIds", departmentIds).executeUpdate();
        entityManager.getTransaction().commit();

        final List<Employee> employees = entityManager.createQuery(GET_EMPLOYEES_BY_DEPARTMENT, Employee.class).getResultList();

        employees.forEach(employee -> System.out.printf(PRINT_FORMAT, employee.getFirstName(), employee.getLastName(), employee.getSalary()));
        entityManager.close();

    }
}
