package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.util.ConnectionFactory;

import java.sql.*;
import java.util.List;

public class ReimbursementDAOPostgres implements ReimbursementDAO{


    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        try(Connection connection = ConnectionFactory.getConnection()){
            //inserting values into SQL table
            String sql = "insert into reimbursement values(default, ?, default , default, ?, default, default, ?, default)";
            PreparedStatement preparedStatement =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting parameters for above SQL statement
            preparedStatement.setInt(1, reimbursement.getReimbursement_id());
            preparedStatement.setInt(2, reimbursement.getAmount());
            preparedStatement.setString(3, reimbursement.getSubmitted());
            preparedStatement.setString(4,reimbursement.getResolved());
            preparedStatement.setString(5, reimbursement.getDescription());
            preparedStatement.setInt(6, reimbursement.getReimbursement_submitter_user_id());
            preparedStatement.setInt(7, reimbursement.getReimbursement_resolved_by_user_id());
            preparedStatement.setString(8, String.valueOf(reimbursement.getStatus()));

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
    public Reimbursement getReimbursementById(int id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql ="select * from reimbursements where id = ?";
            PreparedStatement preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
            reimbursement.setAmount(resultSet.getInt("amount"));
            reimbursement.setSubmitted(resultSet.getString("submitted"));
            reimbursement.setResolved(resultSet.getString("resolved"));
            reimbursement.setDescription(resultSet.getString("description"));
            reimbursement.setReimbursement_submitter_user_id(resultSet.getInt("reimbursement_submitter_user_id"));
            reimbursement.setReimbursement_resolved_by_user_id(resultSet.getInt("reimbursement_resolved_by_user_id"));
            reimbursement.setReimbursement_type_id(resultSet.getInt("reimbursement_type_id"));
            reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));

            return reimbursement;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        return null;
    }

    @Override
    public Reimbursement updateReimbursement(Reimbursement reimbursement) {
        return null;
    }

    @Override
    public boolean deleteReimbursementById(int id) {
        return false;
    }
}
