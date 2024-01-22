package com.coursework.eshop.hibernateController;

import com.coursework.eshop.fxController.JavaFxCustomUtils;
import com.coursework.eshop.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
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

            query.select(root).where(cb.equal(root.get("username"), username));

            TypedQuery<User> typedQuery = entityManager.createQuery(query);
            User user = typedQuery.getSingleResult();

            if (user != null && user.checkPassword(password)) {
                return user;
            }
            return null;
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

    public List<Cart> getAllCartsForSpecificUser(int id) {
        EntityManager em = null;
        List<Cart> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);

            Root<Cart> cart = cq.from(Cart.class);
            Join<Cart, User> userJoin = cart.join("user");

            Predicate userPredicate = cb.equal(userJoin.get("id"), id);

            cq.where(userPredicate);
            result = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public void deleteCart(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cart cart = entityManager.find(Cart.class, id);

            if (cart != null) {
                User user = cart.getOwner();
                if (user != null) {
                    user.getMyCarts().remove(cart);
                    entityManager.merge(user);
                }

                for (Product product : cart.getItemsInCart()) {
                    product.setCart(null);
                    entityManager.merge(product);
                }

                entityManager.remove(cart);
                entityManager.getTransaction().commit();
            } else {
                JavaFxCustomUtils.generateAlert(
                        javafx.scene.control.Alert.AlertType.ERROR,
                        "Error",
                        "Error",
                        "Cart not found");
            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting cart");
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
