package Execise;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ContainsEmployee_02 {
    private final static String GET_ALL_EMPLOYEES = "FROM * Employee";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        final String[] input = new Scanner(System.in).nextLine().split("\\s+");
        final String firstName = input[0];
        final String lastName = input[1];
        final String fullName = firstName + " " + lastName;


        Long matches = entityManager.createQuery(
                        "SELECT COUNT(e) FROM Employee AS e " +
                                "WHERE CONCAT_WS(' ', e.firstName, e.lastName) = :fn", Long.class)
                .setParameter("fn", fullName).getSingleResult();

        if (matches == 0) {
            System.out.println("No");
        }else {
            System.out.println("Yes");
        }

        entityManager.close();
    }
}
