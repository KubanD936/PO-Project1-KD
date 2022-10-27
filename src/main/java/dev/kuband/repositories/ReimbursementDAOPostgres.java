package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.ReimbursementType;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;
import dev.kuband.util.ConnectionFactory;
import dev.kuband.driver.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAOPostgres implements ReimbursementDAO {


    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            //inserting values into SQL table
            String sql = "insert into reimbursement values(default, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting parameters for above SQL statement
            preparedStatement.setString(1, Driver.currentLoggedUsers.getUsername());
            preparedStatement.setInt(2, reimbursement.getAmount());
            preparedStatement.setString(3, reimbursement.getDescription());
            preparedStatement.setString(4, reimbursement.getReimbursementType().name());
            preparedStatement.setString(5, reimbursement.getStatus().name());

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
            reimbursement.setReimbursementType(ReimbursementType.valueOf(resultSet.getString("reimbursementType")));
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
            String sql = "select * from reimbursement";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                reimbursement.setReimbursementType(ReimbursementType.valueOf(resultSet.getString("reimbursementType")));
                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }
            return reimbursementList;

        } catch (SQLException e) {
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
            System.out.println(resultSet.getString("role_type"));
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
                    reimbursement.setReimbursementType(ReimbursementType.valueOf(resultSet.getString("type")));
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
                    reimbursement.setReimbursementType(ReimbursementType.valueOf(resultSet.getString("type")));
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
    public List<Reimbursement> getPendingReimbursement() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                reimbursement.setReimbursementType(ReimbursementType.valueOf(resultSet.getString("reimbursementType")));
                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
