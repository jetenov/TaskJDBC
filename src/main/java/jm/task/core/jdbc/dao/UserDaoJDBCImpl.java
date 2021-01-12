package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users\n" +
                "(\n" +
                "   id bigint not null auto_increment primary key,\n" +
                "   name VARCHAR(50),\n" +
                "   lastName VARCHAR(50),\n" +
                "   age int\n" +
                ");";


        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error creating table");
        }
    }

    public void dropUsersTable() {
        String sql = "drop table IF EXISTS users";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastName, age)\n" +
                "VALUES (?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
            System.out.println("User " + name + " added in data base");
        } catch (Exception e1) {
            System.out.println("User " + name + " didn't add in data base");
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Exception in rollback transaction");
                e2.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println("Exception in closing connection");
                e3.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users\n" +
                "where id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            System.out.println("error removing user by id");
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Exception in rollback transaction");
                e2.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println("Exception in closing connection");
                e3.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select * from users";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                User user = new User(set.getString("name"),set.getString("lastName"), set.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("error getting all users");
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "truncate table users";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e1) {
            System.out.println("error cleaning table");
            e1.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println("Exception in rollback transaction");
                e2.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println("Exception in closing connection");
                e3.printStackTrace();
            }
        }
    }
}
