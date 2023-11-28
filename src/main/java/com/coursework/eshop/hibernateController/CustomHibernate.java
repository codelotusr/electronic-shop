package com.coursework.eshop.hibernateController;

import com.coursework.eshop.fxController.JavaFxCustomUtils;
import com.coursework.eshop.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CustomHibernate extends GenericHibernate {
    public CustomHibernate(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    public User getUserByCredentials(String username, String password) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("username"), username), cb.like(root.get("password"), password)));
            Query q;

            q = entityManager.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while getting user by credentials");
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteProduct(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var product = entityManager.find(Product.class, id);

            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getInStockProducts().remove(product);
                entityManager.merge(warehouse);
            }

            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting product");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteComment(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var comment = entityManager.find(Comment.class, id);

            User user = comment.getAuthor();
            if (user != null) {
                user.getMyComments().remove(comment);
                entityManager.merge(user);
            }

            entityManager.remove(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting comment");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteWarehouse(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var warehouse = entityManager.find(Warehouse.class, id);

            entityManager.remove(warehouse);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting warehouse");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteManager(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var manager = entityManager.find(User.class, id);

            entityManager.remove(manager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting manager");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteCustomer(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var customer = entityManager.find(User.class, id);

            entityManager.remove(customer);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting customer");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteCart(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            var cart = entityManager.find(User.class, id);

            entityManager.remove(cart);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting cart");
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Cart findCartByCustomer(Customer customer) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            query.select(root).where(cb.equal(root.get("customer"), customer));

            TypedQuery<Cart> typedQuery = entityManager.createQuery(query);
            List<Cart> carts = typedQuery.getResultList();

            if (!carts.isEmpty()) {
                return carts.get(0);
            }
            return null;
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while finding cart by customer");
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }



}
