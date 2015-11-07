package master.pam.crud.impl.entity.manager;

import master.pam.crosscutting.dto.base.IIdDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JpaEntityManager {

    private static final String PERSISTENCE_UNIT_NAME = "H2PU";
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static void persist(IIdDto aEntity) {
        getEntityManager().persist(aEntity);
    }

    public static void flush() {
        getEntityManager().flush();
    }

    public static <T> TypedQuery<T> createQuery(String aSqlString,
                                                Class<T> aResultClass) {
        return getEntityManager().createQuery(aSqlString, aResultClass);
    }

    public static <T> TypedQuery<T> createNamedQuery(String aQueryName,
                                                     Class<T> aResultClass) {
        return getEntityManager().createNamedQuery(aQueryName, aResultClass);
    }

    public static <T> T find(Class<T> aResultClass, Long aId) {
        return getEntityManager().find(aResultClass, aId);
    }

    public static void remove(IIdDto aEntity) {
        getEntityManager().remove(aEntity);
    }
}
