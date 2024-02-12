package Execise;

import javax.persistence.EntityManager;

public class EmployeesMaximumSalaries_11 {
    private final static String GET_MAX_SALARY_BY_DEPARTMENT = "SELECT department.name, max(salary)" +
            "  FROM Employee" +
            "  GROUP BY department.name" +
            "  HAVING max(salary) NOT BETWEEN 30000 AND 70000";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        entityManager.createQuery(GET_MAX_SALARY_BY_DEPARTMENT, Object[].class)
                .getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1]));

        entityManager.close();
    }
}

