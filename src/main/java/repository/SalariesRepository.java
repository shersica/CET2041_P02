package repository;

import entities.Salaries;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class SalariesRepository {

    protected EntityManager em;

    public Salaries findLatestSalary(Long empNo) {
        em = JPAUtil.getEntityManager();

        return em.createNamedQuery("Salaries.findLatestEmployeeSalary", Salaries.class)
                .setParameter("empNo", empNo)
                .setMaxResults(1)
                .getSingleResult();
    }
}
