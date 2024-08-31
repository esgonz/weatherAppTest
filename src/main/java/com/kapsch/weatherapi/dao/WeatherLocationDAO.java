package com.kapsch.weatherapi.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.kapsch.weatherapi.model.WeatherLocation;

public class WeatherLocationDAO {

    private static final String PERSISTENCE_UNIT_NAME = "weatherPU";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

    public void save(WeatherLocation location) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(location);
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

    // new untested method
    public WeatherLocation findByLatitudeAndLongitude(double latitude, double longitude) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            TypedQuery<WeatherLocation> query = entityManager.createQuery(
                    "SELECT wl FROM WeatherLocation wl WHERE wl.latitude = :latitude AND wl.longitude = :longitude",
                    WeatherLocation.class
            );
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            query.setMaxResults(1); // Ensure only one result is retrieved
            return query.getSingleResult();
        } catch (NoResultException e) {
            // No result found, return null
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }
}
