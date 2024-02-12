package Execise;

import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns_12 {
    private final static String GET_TOWN_FOR_DELETE = "FROM Town AS t WHERE t.name = :input";
    private final static String GET_ADDRESSES_FOR_DELETE_BY_TOWN = "FROM Address AS a WHERE a.town.id = :t";
    private final static String PRINT_FORMAT = "%d address%s in %s deleted";

    public static void main(String[] args) {
        final EntityManager entityManager = Utils.creatEntityManger();

        final String givenTownForDelete = new Scanner(System.in).nextLine();

        entityManager.getTransaction().begin();

        Town townToDelete = getTownByName(entityManager, givenTownForDelete);

        List<Address> addressesInDeletedTown = getAddressesByTown(entityManager, townToDelete);

        deleteAddressesAndTown(entityManager, addressesInDeletedTown, townToDelete);

        printDeletedCountOfDeletedAddresses(addressesInDeletedTown, givenTownForDelete);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static Town getTownByName(EntityManager entityManager, String town) {
        return entityManager.createQuery(GET_TOWN_FOR_DELETE, Town.class).setParameter("input", town).getSingleResult();
    }

    private static List<Address> getAddressesByTown(EntityManager entityManager, Town town) {
        return entityManager.createQuery(GET_ADDRESSES_FOR_DELETE_BY_TOWN, Address.class)
                .setParameter("t", town.getId()).getResultList();

    }

    private static void deleteAddressesAndTown(EntityManager entityManager, List<Address> addresses, Town town) {
        addresses.forEach(address -> address.getEmployees().forEach(e -> e.setAddress(null)));
        addresses.forEach(entityManager::remove);
        entityManager.remove(town);
    }

    private static void printDeletedCountOfDeletedAddresses(List<Address> addresses, String town){
        System.out.printf(PRINT_FORMAT, addresses.size(), addresses.size() == 1 ? "" : "es", town);
    }
}
