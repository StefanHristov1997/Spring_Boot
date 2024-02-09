package Execise;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class AddingANewAddressAndUpdatingTheEmployee_05 {

    private final static String GET_EMPLOYEE_BY_LAST_NAME = "SELECT e FROM Employee AS e" +
            " WHERE e.lastName = :in";
    private final static String NEW_ADDRESS = "Vitoshka 15";

    public static void main(String[] args) {
        EntityManager entityManager = Utils.creatEntityManger();

        String input = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();
        Address address = new Address();
        address.setText(NEW_ADDRESS);
        entityManager.persist(address);

        Employee employee = entityManager.createQuery(GET_EMPLOYEE_BY_LAST_NAME, Employee.class).setParameter("in", input).getSingleResult();

        employee.setAddress(address);
        entityManager.persist(employee);

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
