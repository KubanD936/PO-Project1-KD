package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;

import java.util.List;

public interface ReimbursementDAO {

    //Create reimbursement
    Reimbursement createReimbursement(Reimbursement reimbursement);

    //Retrieve reimbursement
    Reimbursement getReimbursementById(int reimbursement_id);

    //Retrieve pending reimbursement requests
    List<Reimbursement> getPendingReimbursement();

    //Retrieve all reimbursements
    List<Reimbursement> getAllReimbursement();



    //Retrieve reimbursement based on user
    //List<Reimbursement> getReimbursementByUser(User user);

    //Update reimbursement
    Reimbursement updateReimbursement(Reimbursement reimbursement);

    //Delete reimbursement
    boolean deleteReimbursementById(int reimbursement_id);

}
