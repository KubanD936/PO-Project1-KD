package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;
import dev.kuband.util.ConnectionFactory;

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
            preparedStatement.setString(3, String.valueOf(users.isAdmin()));

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
            users.setRole_type(RoleType.valueOf(resultSet.getString("role_type")));

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
                users.setRole_type(RoleType.valueOf(resultSet.getString("role_type")));
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
            String sql = "update users set username = ?, password = ?, first_name = ?, last_name = ?, email = ?, role_type = ? where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setString(6, users.getRole_type().name());

            preparedStatement.executeUpdate();
            return users;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String usersGetReimbursements() {
        return null;
    }

    @Override
    public String updateAdminPrivilege(Users users) {
        return null;
    }

    @Override
    public String updateUsersInfo(Users users) {
        return null;
    }

    @Override
    public boolean deleteUsersById(int user_id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "delete from users where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, user_id);

            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
