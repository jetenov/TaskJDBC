package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

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

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error creating table");
        }
    }

    public void dropUsersTable() {
        String sql = "drop table IF EXISTS users";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error drop table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (name, lastName, age)\n" +
                "VALUES (?, ?, ?);";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User " + name + " added in data base");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("User " + name + " didn't add in data base");
        }
    }

    public void removeUserById(long id) {
        String sql = "delete from users\n" +
                "where id = ?";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error removing user by id");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "select * from users";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                User user = new User(set.getString("name"),set.getString("lastName"), set.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error getting all users");
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "truncate table users";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error cleaning table");
        }
    }
}
