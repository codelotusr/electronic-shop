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
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var comment = em.find(Comment.class, id);

            if (comment.getClass() == Review.class) {
                Review review = (Review) comment;
                Product product = em.find(Product.class, review.getProduct().getId());
                product.getReviews().remove(review);
                em.merge(product);
            } else {
                comment.getReplies().clear();
                em.remove(comment);

            }
            em.getTransaction().commit();
        } catch (Exception e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting comment");
        } finally {
            if (em != null) em.close();
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

    public List<Product> getAvailableProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.isNull(root.get("cart")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while getting available products");
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void addToCart(int userId, List<Product> productList) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            User user = readEntityById(User.class, userId);
            Cart cart = new Cart(user);
            for (Product p : productList) {
                Product product = readEntityById(Product.class, p.getId());
                product.setCart(cart);
                cart.getItemsInCart().add(product);
            }
            user.getMyCarts().add(cart);
            em.merge(user);

            em.getTransaction().commit();
        } catch (NoResultException e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while adding to cart");
        } finally {
            if (em != null) em.close();
        }
    }

}
