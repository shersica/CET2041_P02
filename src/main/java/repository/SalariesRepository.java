package repository;

import entities.Salaries;
import entities.SalariesId;
import jakarta.persistence.EntityManager;

public class SalariesRepository {

    public void addSalary(EntityManager em, Salaries salaries) {
        em.persist(salaries);
    }

    public Salaries findById(EntityManager em, SalariesId id) {
        return em.find(Salaries.class, id);
    }
}
