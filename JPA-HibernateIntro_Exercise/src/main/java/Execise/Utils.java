package Execise;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public enum Utils {
    ;
    public static EntityManager creatEntityManger(){
        return Persistence.createEntityManagerFactory("soft_uni").createEntityManager();
    }
}
