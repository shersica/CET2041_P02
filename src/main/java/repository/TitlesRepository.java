package repository;

import entities.Titles;
import entities.TitlesId;
import jakarta.persistence.EntityManager;

/**
 * Repository providing JPA operations for {@link Titles} entity.
 * Does not manage transactions; the caller is responsible for beginning
 * and committing/rolling back transactions.
 */
public class TitlesRepository {

    /**
     * Add a {@code Titles} object to the {@code titles} table. No return type.
     * @param em active EntityManager
     * @param title Titles object to be added
     */
    public void addTitle(EntityManager em, Titles title) {
        em.persist(title);
    }

    /**
     * Find and return {@code Titles} by its composite key, i.e. {@code TitlesId}
     * @param em active EntityManager
     * @param id {@code TitlesId} object
     * @return the {@code Titles} object if found, otherwise null
     */
    public Titles findById(EntityManager em, TitlesId id) {
        return em.find(Titles.class, id);
    }
}
