package repository;

import entities.Titles;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class TitlesRepository {
    protected EntityManager em;

    public Titles findLatestTitle(Long empNo) {
        em = JPAUtil.getEntityManager();

        return em.createNamedQuery("Titles.findLatestEmployeeTitle", Titles.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }
}
