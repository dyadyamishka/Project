package org.example.entity;

import org.example.entity.Habit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class HabitRepository {
    private EntityManagerFactory emf;

    public HabitRepository() {
        emf = Persistence.createEntityManagerFactory("habitPU");
    }

    public void save(Habit habit) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(habit);
        em.getTransaction().commit();
        em.close();
    }

    public List<Habit> findAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Habit> query = em.createQuery("SELECT h FROM Habit h", Habit.class);
        List<Habit> habits = query.getResultList();
        em.close();
        return habits;
    }


    public Habit findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Habit habit = em.find(Habit.class, id);
        em.close();
        return habit;
    }

    public void delete(Habit habit) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(habit) ? habit : em.merge(habit));
        em.getTransaction().commit();
        em.close();
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public Habit findByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Habit> query = em.createQuery("SELECT h FROM Habit h WHERE h.name = :name", Habit.class);
        query.setParameter("name", name);

        List<Habit> habits = query.getResultList();
        em.close();

        return habits.isEmpty() ? null : habits.get(0);
    }
}