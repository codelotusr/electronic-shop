package com.coursework.eshop.hibernateController;

import com.coursework.eshop.model.Warehouse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class WarehouseHibernate {
    private EntityManagerFactory entityManagerFactory;

    public WarehouseHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createWarehouse(Warehouse warehouse) {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(warehouse);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }




}
