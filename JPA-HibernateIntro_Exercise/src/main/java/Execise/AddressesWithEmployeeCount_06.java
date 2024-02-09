package Execise;

import entities.Address;

import javax.persistence.EntityManager;

public class AddressesWithEmployeeCount_06 {
    private final static String PRINT_FORMAT = "%s, %s, %d employees\n";
    public static void main(String[] args) {
        EntityManager entityManager = Utils.creatEntityManger();

        entityManager.createQuery("SELECT a FROM Address AS a ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10).getResultList()
                .forEach(address -> System.out.printf(PRINT_FORMAT,
                        address.getText(),
                        address.getTown().getName(),
                        address.getEmployees().size()));

    }
}
