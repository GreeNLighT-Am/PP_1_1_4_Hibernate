package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sqlQuery = """
                CREATE TABLE IF NOT EXISTS mydbtest.users (
                    id INT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(45) NULL,
                    lastname VARCHAR(45) NULL,
                    age INT NULL,
                    PRIMARY KEY (id));
                """;
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка в createUsersTable: " + e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        String sqlQuery = "DROP TABLE IF EXISTS users";
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка в dropUsersTable: " + e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка в saveUser: " + e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка в removeUserById: " + e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        String sqlQuery = "FROM User";

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(sqlQuery, User.class).getResultList();
        } catch (HibernateException e) {
            System.err.println("Ошибка в getAllUsers: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void cleanUsersTable() {

        String sqlQuery = "DELETE FROM User";
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Ошибка в cleanUsersTable: " + e.getMessage());

            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}