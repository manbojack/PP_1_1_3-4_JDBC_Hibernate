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
        Connection connection = null;
        Savepoint savepoint = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint("Savepoint");
            statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users " +
                            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(255), " +
                            "lastName VARCHAR(255), " +
                            "age INT)"
            );
            connection.commit();
            System.out.println("[INFO]: Таблица users была создана.");
        } catch (SQLException e) {
            System.out.println("[ERROR]: Таблица users не создана.");
            rollbackBDChanges(connection, savepoint);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Savepoint savepoint = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint("savepoint");
            statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();
            System.out.println("[INFO]: Таблица users была удалена.");
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackBDChanges(connection, savepoint);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        Savepoint savepoint = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint("savepoint");
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackBDChanges(connection, savepoint);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        Savepoint savepoint = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint("savepoint");
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackBDChanges(connection, savepoint);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = null;
        ResultSet resultSet = null;
        List<User> users = null;
        try {
            connection = Util.getConnection();
            resultSet = connection
                    .createStatement()
                    .executeQuery("SELECT * FROM users");
            users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        Savepoint savepoint = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint("savepoint");
            statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users");
            connection.commit();
            System.out.println("[INFO]: Таблица users была очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
            rollbackBDChanges(connection, savepoint);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User getUserById(long id) {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = Util.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")
                );
                user.setId(resultSet.getLong("id"));
            } else {
                System.out.println("[WARNING]: User c id=" + id + " не найден.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    private void rollbackBDChanges(Connection connection, Savepoint savepoint) {
        try {
            System.out.println("[INFO]: Откат БД ...");
            connection.rollback(savepoint);
            System.out.println("[INFO]: БД откатилась.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
