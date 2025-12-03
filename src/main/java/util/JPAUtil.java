package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    private static EntityManagerFactory emf = null;

    static {
//        Map<String, String> persistenceMap = new HashMap<>();

        // CHANGE DATABASE NAME HERE --------------
//        String DBNAME = "employees";
//
//        persistenceMap.put("jakarta.persistence.jdbc.url",
//                "jdbc:mariadb://localhost:3306/" + DBNAME);

        try {
            System.out.println("Initializing EntityManagerFactory...");
//            emf = Persistence.createEntityManagerFactory("EmployeeRepository", persistenceMap);
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
