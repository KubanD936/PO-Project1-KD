package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAOPostgres implements ReimbursementDAO{


    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        try(Connection connection = ConnectionFactory.getConnection()){
            //inserting values into SQL table
            String sql = "insert into reimbursement values(default, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement =connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting parameters for above SQL statement
            preparedStatement.setInt(1, reimbursement.getAmount());
            preparedStatement.setString(2, reimbursement.getSubmitted());
            preparedStatement.setString(3,reimbursement.getResolved());
            preparedStatement.setString(4, reimbursement.getDescription());
            preparedStatement.setInt(5, reimbursement.getReimbursement_submitter_user_id());
            preparedStatement.setInt(6, reimbursement.getReimbursement_resolved_by_user_id());
            preparedStatement.setInt(7, reimbursement.getReimbursement_type_id());
            preparedStatement.setString(8, String.valueOf(reimbursement.getStatus()));

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int generatedKey = resultSet.getInt("reimbursement_id");
            reimbursement.setReimbursement_id(generatedKey);
            return reimbursement;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursement_id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from reimbursements where reimbursement_id = ?";
            PreparedStatement preparedStatement =connection.prepareStatement(sql);
            preparedStatement.setInt(1,reimbursement_id);

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

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "select * from reimbursement";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Reimbursement> reimbursementList = new ArrayList();

            while(resultSet.next()){
                Reimbursement reimbursement =new Reimbursement();
                reimbursement.setReimbursement_id(resultSet.getInt("reimbursement_id"));
                reimbursement.setAmount(resultSet.getInt("amount"));
                reimbursement.setSubmitted(resultSet.getString("submitted"));
                reimbursement.setResolved(resultSet.getString("resolved"));
                reimbursement.setDescription(resultSet.getString("description"));
                reimbursement.setReimbursement_submitter_user_id(resultSet.getInt("reimbursement_submitter_user_id"));
                reimbursement.setReimbursement_resolved_by_user_id(resultSet.getInt("reimbursement_resolved_by_user_id"));
                reimbursement.setReimbursement_type_id(resultSet.getInt("reimbursement_type_id"));
                reimbursement.setStatus(Status.valueOf(resultSet.getString("status")));
                reimbursementList.add(reimbursement);
            }
            return reimbursementList;

        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Reimbursement updateReimbursement(Reimbursement reimbursement) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "update reimbursement set amount = ?, submitted = ?, resolved = ?, description = ?, reimbursement_submitter_user_id = ?, reimbursement_resolved_by_user_id = ?, reimbursement_type_id = ?, status = ? where reimbursement_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, reimbursement.getAmount());
            preparedStatement.setString(2, reimbursement.getSubmitted());
            preparedStatement.setString(3,reimbursement.getResolved());
            preparedStatement.setString(4, reimbursement.getDescription());
            preparedStatement.setInt(5, reimbursement.getReimbursement_submitter_user_id());
            preparedStatement.setInt(6, reimbursement.getReimbursement_resolved_by_user_id());
            preparedStatement.setInt(7, reimbursement.getReimbursement_type_id());
            preparedStatement.setString(8, String.valueOf(reimbursement.getStatus()));
            preparedStatement.setInt(9, reimbursement.getReimbursement_id());

            preparedStatement.executeUpdate();
            return reimbursement;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteReimbursementById(int reimbursement_id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "delete from reimbursement where reimbursement_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, reimbursement_id);

            preparedStatement.execute();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
