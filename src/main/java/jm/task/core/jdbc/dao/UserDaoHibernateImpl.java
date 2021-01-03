package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User\n" +
                    "(\n" +
                    "   id bigint not null auto_increment primary key,\n" +
                    "   name VARCHAR(50),\n" +
                    "   lastName VARCHAR(50),\n" +
                    "   age int\n" +
                    ");";
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createSQLQuery("drop table IF EXISTS User").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> list = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            list = session.createQuery("from User").list();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
