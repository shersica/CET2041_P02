package repository;

import entities.Titles;
import entities.TitlesId;
import jakarta.persistence.EntityManager;

public class TitlesRepository {

    public void addTitle(EntityManager em, Titles title) {
        em.persist(title);
    }

    public Titles findById(EntityManager em, TitlesId id) {
        return em.find(Titles.class, id);
    }
}
