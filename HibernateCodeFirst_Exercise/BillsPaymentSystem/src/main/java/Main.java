import entities.BankAccount;
import entities.BillingDetail;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("soft_uni").createEntityManager();

        entityManager.getTransaction().begin();
        User user = new User("Stefan", "Hristov", "stefan@abv.bg", "1234");
        BillingDetail billingDetail = new BankAccount("Credit", "1234");
        entityManager.persist(billingDetail);
        user.getBillingDetails().add(billingDetail);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
