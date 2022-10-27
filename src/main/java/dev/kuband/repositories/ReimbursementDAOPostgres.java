package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
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
            String sql = "insert into reimbursement values(default, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting parameters for above SQL statement
            preparedStatement.setString(1, Driver.currentLoggedUsers.getUsername());
            preparedStatement.setInt(2, reimbursement.getAmount());
            preparedStatement.setString(3, reimbursement.getDescription());
            preparedStatement.setString(4, reimbursement.getStatus().name());

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
            String sql = "select * from reimbursement";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
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
        if (Driver.currentLoggedUsers == null){
            return "You are not logged in";
        } else if(!Driver.currentLoggedUsers.isAdmin()){
            return "Need to be admin to change this";
        }
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from reimbursement where reimbursement_id = ?";
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
            sql = "update reimbursement set status = ? where reimbursement_id = ?";
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

/*    @Override
    public List<Reimbursement> getPendingReimbursement() {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "select * from reimbursement where status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Status.PENDING));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setDescription(resultSet.getString("description"));
                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }
            return reimbursementList;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
