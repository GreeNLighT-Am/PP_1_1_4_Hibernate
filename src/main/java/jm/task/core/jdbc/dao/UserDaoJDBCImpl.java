package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS `mydbtest`.`users` (\n" +
                    "                                    `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "                                    `name` VARCHAR(45) NULL,\n" +
                    "                                    `lastname` VARCHAR(45) NULL,\n" +
                    "                                    `age` INT NULL,\n" +
                    "                                    PRIMARY KEY (`id`));");

        } catch (SQLException e) {
            System.out.println("Ошибка в createUsersTable");
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS users;");

        } catch (SQLException e) {
            System.out.println("Ошибка в dropUsersTable");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Ошибка в saveUser");
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM users WHERE id=?;";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Ошибка в removeUserById");
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users;");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка в getAllUsers");
        }

        return users;
    }

    public void cleanUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("TRUNCATE TABLE users;");

        } catch (SQLException e) {
            System.out.println("Ошибка в cleanUsersTable");
        }
    }
}