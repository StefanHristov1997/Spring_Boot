package Execise;

import javax.persistence.EntityManager;

public class EmployeesWithSalaryOver50000_03 {
    private final static String GET_EMPLOYEES_WITH_SALARY_ABOVE_50000 = "SELECT e.firstName FROM Employee AS e WHERE e.salary > 50000";
    public static void main(String[] args) {
        EntityManager entityManager = Utils.creatEntityManger();

        entityManager.createQuery(GET_EMPLOYEES_WITH_SALARY_ABOVE_50000, String.class)
                .getResultList().forEach(System.out::println);

        entityManager.close();
    }
}
