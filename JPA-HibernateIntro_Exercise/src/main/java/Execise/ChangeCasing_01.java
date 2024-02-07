package Execise;

import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ChangeCasing_01 {
    private final static String FILTER_TOWN_NAMES_LESS_THAN_5_SYMBOLS = "FROM Town WHERE LENGTH(name) < 5";

    public static void main(String[] args) {

        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        final EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        final List<Town> townNamesWithLessThanFiveSymbols = entityManager.createQuery(FILTER_TOWN_NAMES_LESS_THAN_5_SYMBOLS, Town.class).getResultList();

        for (Town town : townNamesWithLessThanFiveSymbols) {
            town.setName(town.getName().toUpperCase());
            entityManager.persist(town);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
