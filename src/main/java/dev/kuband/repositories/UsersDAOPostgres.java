package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;
import dev.kuband.util.ConnectionFactory;
import dev.kuband.driver.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*public class UsersDAOPostgres implements UsersDAO{
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
            System.out.println(users);
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

    *//*@Override
    public Users getUsersByUsername(String username) {
        return null;
    }*//*

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
    public ArrayList<Reimbursement> login(Users users) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            ArrayList<Reimbursement> reimbursements = new ArrayList<>();
            String sql = "select * from users where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Users verified = new Users();
            verified.setUser_id(resultSet.getInt("user_id"));
            verified.setUsername(resultSet.getString("username"));
            verified.setPassword(resultSet.getString("password"));
            verified.setAdmin(resultSet.getBoolean("isAdmin"));
            *//*System.out.println(resultSet.getString("role_type"));*//*
            Driver.currentLoggedUsers = verified;
            if (verified.isAdmin()) {
                sql = "select * from reimbursement where status = ?";
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "PENDING");

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Reimbursement reimbursement = new Reimbursement();
                    reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_d"));
                    reimbursement.setUser(resultSet.getString("username"));
                    reimbursement.setAmount(resultSet.getInt("amount"));
                    reimbursement.setDescription(resultSet.getString("description"));
                    reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                    reimbursements.add(reimbursement);
                }
                return reimbursements;
            } else {
                sql = "select * from reimbursement where username = ?";
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, users.getUsername());

                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Reimbursement reimbursement = new Reimbursement();
                    reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                    reimbursement.setUser(resultSet.getString("username"));
                    reimbursement.setAmount(resultSet.getInt("amount"));
                    reimbursement.setDescription(resultSet.getString("description"));
                    reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                    reimbursements.add(reimbursement);
                }
                return reimbursements;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String updateUsers(Users users) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "update users set username = ?, password = ?, isAdmin = ? where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());
            preparedStatement.setBoolean(3, users.isAdmin());

            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Change failed\r\nInvalid user";
            } else {
                return "Change success"+users.getUser_id() + " this user was updated";
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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


}*/
