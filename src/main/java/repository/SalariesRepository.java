package repository;

import entities.Salaries;
import entities.SalariesId;
import jakarta.persistence.EntityManager;

/**
 * Repository providing JPA operations for {@link Salaries} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class SalariesRepository {

    /**
     * Add {@code Salaries} object to the database. No return type.
     * @param em active EntityManager
     * @param salaries Salaries object to be added
     */
    public void addSalary(EntityManager em, Salaries salaries) {
        em.persist(salaries);
    }

    /**
     * Find and return Salaries from its composite key, i.e. SalariesId
     * @param em active EntityManager
     * @param id SalariesId to query
     * @return the Salaries object if found, otherwise null
     */
    public Salaries findById(EntityManager em, SalariesId id) {
        return em.find(Salaries.class, id);
    }
}
