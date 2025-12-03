package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    private static EntityManagerFactory emf = null;

    static {
        try {
            System.out.println("Initializing EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("EmployeePU");
            System.out.println("EntityManagerFactory initialized!");
        } catch (Exception e) {
            System.out.println("EntityManagerFactory initialization failed!");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && !emf.isOpen()) {
            emf.close();
        }
    }
}
