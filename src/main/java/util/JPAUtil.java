package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for obtaining application-managed EntityManager
 * instances. This helper encapsulates creation of the
 * {@link EntityManagerFactory}.
 */
public class JPAUtil {
    /**
     * A single shared {@link EntityManagerFactory} for the application.
     *
     * <p>This factory is created from the {@code EmployeeRepository} persistence
     * unit defined in {@code persistence.xml}. Because creating an
     * {@code EntityManagerFactory} is an expensive operation, it is initialized
     * once and reused throughout the application to create lightweight
     * {@link jakarta.persistence.EntityManager} instances.
     *
     * <p>The factory is thread-safe and should remain alive for the duration of
     * the application's lifecycle. It is closed only during application shutdown.
     */
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

    /**
     * Returns a new EntityManager instance.
     *
     * @return fresh EntityManager (caller is responsible for closing it).
     */
    public static EntityManager getEntityManager() {
        EntityManagerFactory factory = getEntityManagerFactory();
        return factory.createEntityManager();
    }

    /**
     * Close the existing and open {@link EntityManagerFactory}
     * at the end of the operation.
     */
    public static void shutdown() {
        if (emf != null && !emf.isOpen()) {
            emf.close();
        }
    }
}
