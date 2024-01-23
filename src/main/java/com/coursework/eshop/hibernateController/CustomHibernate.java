package com.coursework.eshop.hibernateController;

import com.coursework.eshop.fxController.JavaFxCustomUtils;
import com.coursework.eshop.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
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

    public List<Cart> filterData(double minCost, double maxCost, int userId, LocalDate fromDate, LocalDate toDate) {
        EntityManager em = null;
        List<Cart> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);

            Root<Cart> cart = cq.from(Cart.class);
            Join<Cart, User> userJoin = cart.join("owner");
            List<Predicate> predicates = new ArrayList<>();

            if (minCost > 0) {
                predicates.add(cb.ge(cart.get("cart_value"), minCost));
            }

            if (maxCost > 0) {
                predicates.add(cb.le(cart.get("cart_value"), maxCost));
            }

            if (userId > 0) {
                predicates.add(cb.equal(userJoin.get("id"), userId));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(cart.get("dateCreated"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(cart.get("dateCreated"), toDate));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            result = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while filtering data");
        } finally {
            if (em != null) em.close();
        }
        return result;
    }

    public List<Comment> readAllRootComments() {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
            Root<Comment> root = query.from(Comment.class);

            query.select(root).where(cb.isNull(root.get("parentComment")));

            TypedQuery<Comment> typedQuery = entityManager.createQuery(query);
            return typedQuery.getResultList();
        } catch (NoResultException e) {
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while getting root comments");
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteComment(int id) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comment comment = entityManager.find(Comment.class, id);
            deleteCommentAndReplies(entityManager, comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            JavaFxCustomUtils.generateAlert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Error while deleting comment");
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    private void deleteCommentAndReplies(EntityManager em, Comment comment) {
        if (comment == null) return;

        if (!em.contains(comment)) {
            comment = em.merge(comment);
        }

        for (Comment reply : new ArrayList<>(comment.getReplies())) {
            deleteCommentAndReplies(em, reply);
        }

        if (comment.getParentComment() != null) {
            comment.getParentComment().getReplies().remove(comment);
            em.merge(comment.getParentComment());
        }

        User user = comment.getUser();
        if (user != null) {
            user.getMyComments().remove(comment);
            em.merge(user);
        }

        if (comment instanceof Review) {
            Review review = (Review) comment;
            Product product = review.getProduct();
            if (product != null) {
                product.getReviews().remove(review);
                em.merge(product);
            }
        }

        em.remove(comment);
    }
}
