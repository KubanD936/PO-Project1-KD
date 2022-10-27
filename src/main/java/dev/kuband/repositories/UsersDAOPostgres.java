package dev.kuband.repositories;

import dev.kuband.entities.Users;
import dev.kuband.util.ConnectionFactory;
import dev.kuband.driver.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAOPostgres implements UsersDAO{
    @Override
    public Users createUsers(Users users) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "insert into users values(default, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setBoolean(3, users.isAdmin());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("user_id");
            users.setUser_id(generatedKey);
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Users getUsersById(int user_id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from users where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Users users = new Users();
            users.setUser_id(resultSet.getInt("user_id"));
            users.setUsername(resultSet.getString("username"));
            users.setPassword(resultSet.getString("password"));
            users.setAdmin(resultSet.getBoolean("isAdmin"));

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Users getUsersByUsername(String username) {
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from users";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Users> usersList = new ArrayList();

            while (resultSet.next()){
                Users users = new Users();
                users.setUser_id(resultSet.getInt("user_id"));
                users.setUsername(resultSet.getString("username"));
                users.setPassword(resultSet.getString("password"));
                users.setAdmin(resultSet.getBoolean("isAdmin"));
                usersList.add(users);
            }
            return usersList;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Users updateUsers(Users users) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "update users set username = ?, password = ?, isAdmin = ? where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setBoolean(3, users.isAdmin());

            preparedStatement.executeUpdate();
            return users;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String updateAdminPrivilege(Users users) {
        if (Driver.currentLoggedUsers == null){
            return "You are not logged in";
        } else if(!Driver.currentLoggedUsers.isAdmin()){
            return "Need to be admin to edit this";
        } else if(users.getUser_id() == Driver.currentLoggedUsers.getUser_id()){
            return "You can't modify your own account";
        }
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "update users set isAdmin = ? where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, users.isAdmin());
            preparedStatement.setInt(2, users.getUser_id());
            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Change failed\r\nMake sure your id is valid";
            } else {
                if (users.isAdmin()){
                    return "User " + users.getUser_id() + " has been changed to Admin";
                } else {
                    return "User " + users.getUser_id() + " has been changed to User";
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
