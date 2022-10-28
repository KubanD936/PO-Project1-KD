package dev.kuband.repositories;

import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;
import dev.kuband.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersAndReimbursementDAOPostgres implements UsersAndReimbursementDAO{
    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            //inserting values into SQL table
            String sql = "insert into reimbursements values(default, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting parameters for above SQL statement
            if(Driver.currentUsers == null){
                preparedStatement.setString(1, reimbursement.getUser());
            }
            else {
                System.out.println(Driver.currentUsers.getUsername());
                preparedStatement.setString(1, Driver.currentUsers.getUsername());
            }
            preparedStatement.setInt(2, reimbursement.getAmount());
            preparedStatement.setString(3, reimbursement.getDescription());
            preparedStatement.setString(4, String.valueOf(reimbursement.getStatus()));

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("reimbursement_id");
            reimbursement.setReimbursement_id(generatedKey);
            return reimbursement;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursement_id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursements where reimbursement_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reimbursement_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
            reimbursement.setAmount(resultSet.getInt("amount"));
            reimbursement.setDescription(resultSet.getString("description"));
            reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));

            return reimbursement;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursements where status = 'PENDING'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                Status status = Status.valueOf(resultSet.getString("status"));
                if(status != null){
                    reimbursement.setStatus(status);
                }
                else{
                    reimbursement.setStatus(Status.PENDING);
                }
//                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }
            return reimbursementList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reimbursement> getAllReimbursementByUsers(Users users) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursements where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, users.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                Status status = Status.valueOf(resultSet.getString("status"));
                if(status != null){
                    reimbursement.setStatus(status);
                }
                else{
                    reimbursement.setStatus(Status.PENDING);
                }
//                reimbursement.setUser(users.getUsername());
//                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }
            return reimbursementList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String changeStatus(int reimbursement_id, Status status) {
        if (Driver.currentUsers == null){
//            return "You are not logged in";
        } else if(!Driver.currentUsers.isAdmin()){
            return "Need to be admin to change this";
        }
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from reimbursements where reimbursement_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, reimbursement_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
            reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
            if (reimbursement.getStatus().equals(Status.APPROVED)){
                return "This reimbursement request has been approved and can't be changed";
            }
            if (reimbursement.getStatus().equals(Status.DENIED)){
                return "This reimbursement request has been denied and can't be changed";
            }
            sql = "update reimbursements set status = ? where reimbursement_id = ?";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, reimbursement_id);

            int rs = preparedStatement.executeUpdate();
            if (rs == 0){
                return "Change failed\r\nVerify reimbursement id and status are correct";
            } else {
                return "Change success, reimbursement request " + reimbursement_id + " has been changed to " + status;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

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

//    @Override
//    public Users getUsersById(int user_id) {
//        try(Connection connection = ConnectionFactory.getConnection()){
//            String sql = "select * from users where user_id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//            Users users = new Users();
//            users.setUser_id(resultSet.getInt("user_id"));
//            users.setUsername(resultSet.getString("username"));
//            users.setPassword(resultSet.getString("password"));
//            users.setAdmin(resultSet.getBoolean("isAdmin"));
//
//            return users;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    @Override
//    public List<Users> getAllUsers() {
//        try(Connection connection = ConnectionFactory.getConnection()){
//            String sql = "select * from users";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            List<Users> usersList = new ArrayList();
//
//            while (resultSet.next()){
//                Users users = new Users();
//                users.setUser_id(resultSet.getInt("user_id"));
//                users.setUsername(resultSet.getString("username"));
//                users.setPassword(resultSet.getString("password"));
//                users.setAdmin(resultSet.getBoolean("isAdmin"));
//                usersList.add(users);
//            }
//            return usersList;
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public boolean loginUsers(Users users) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from users where username = ? and password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, users.getUsername());
            preparedStatement.setString(2, users.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.isBeforeFirst()){
                resultSet.next();

                Users loggedUsers = new Users();
                loggedUsers.setUser_id(resultSet.getInt("user_id"));
                loggedUsers.setUsername(resultSet.getString("username"));
                loggedUsers.setPassword(resultSet.getString("password"));
                loggedUsers.setAdmin(resultSet.getBoolean("isAdmin"));
                Driver.currentUsers = loggedUsers;
                return true;
            }else {
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    /*@Override
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
*/
//    @Override
//    public String updateUsers(Users users) {
//        try(Connection connection = ConnectionFactory.getConnection()){
//            String sql = "update users set username = ?, password = ?, isAdmin = ? where user_id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            preparedStatement.setString(1, users.getUsername());
//            preparedStatement.setString(2, users.getPassword());
//            preparedStatement.setBoolean(3, users.isAdmin());
//
//            int rs = preparedStatement.executeUpdate();
//            if (rs == 0){
//                return "Change failed\r\nInvalid user";
//            } else {
//                return "Change success"+users.getUser_id() + " this user was updated";
//            }
//        } catch(SQLException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

//    @Override
//    public String updateAdminPrivilege(Users users) {
//        if (Driver.currentUsers == null){
//            return "You are not logged in";
//        } else if(!Driver.currentUsers.isAdmin()){
//            return "Need to be admin to edit this";
//        } else if(users.getUser_id() == Driver.currentUsers.getUser_id()){
//            return "You can't modify your own account";
//        }
//        try(Connection connection = ConnectionFactory.getConnection()){
//            String sql = "update users set isAdmin = ? where user_id = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setBoolean(1, users.isAdmin());
//            preparedStatement.setInt(2, users.getUser_id());
//            int rs = preparedStatement.executeUpdate();
//            if (rs == 0){
//                return "Change failed\r\nMake sure your id is valid";
//            } else {
//                if (users.isAdmin()){
//                    return "User " + users.getUser_id() + " has been changed to Admin";
//                } else {
//                    return "User " + users.getUser_id() + " has been changed to User";
//                }
//            }
//        } catch(SQLException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
