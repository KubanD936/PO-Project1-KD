package dev.kuband.services;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;

import java.util.List;

public interface ReimbursementService {
    //Create reimbursement
    Reimbursement createReimbursement(Reimbursement reimbursement);

    Reimbursement getReimbursementById(int reimbursement_id);

    String changeReimbursementStatus(int reimbursement_id, Status status);

    List<Reimbursement> getAllReimbursement();

    /*List<Reimbursement> getPendingReimbursement();*/

}
