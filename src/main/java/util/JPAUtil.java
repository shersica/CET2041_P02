package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    private static EntityManagerFactory emf;

    private JPAUtil() {}

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("EmployeePU");
            } catch (Exception e) {
                System.out.println("EntityManagerFactory initialization failed!");
                throw new ExceptionInInitializerError(e);
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        EntityManagerFactory factory = getEntityManagerFactory();
        return factory.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && !emf.isOpen()) {
            emf.close();
        }
    }
}
