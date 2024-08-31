package com.kapsch.weatherapi.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.kapsch.weatherapi.model.WeatherDetail;
import com.kapsch.weatherapi.model.WeatherLocation;

public class WeatherDetailDAO {

    private static final String PERSISTENCE_UNIT_NAME = "weatherPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public void save(WeatherDetail detail) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(detail);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public List<WeatherDetail> findByLocation(WeatherLocation location) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<WeatherDetail> query = entityManager.createQuery(
                    "SELECT wd FROM WeatherDetail wd WHERE wd.weatherLocation = :location",
                    WeatherDetail.class
            );
            query.setParameter("location", location);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
