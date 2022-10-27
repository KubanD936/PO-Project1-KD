package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;

import java.util.ArrayList;
import java.util.List;

public interface ReimbursementDAO {

    //Create reimbursement
    Reimbursement createReimbursement(Reimbursement reimbursement);

    //Retrieve pending reimbursement requests
    List<Reimbursement> getReimbursementByStatus();

    ArrayList<Reimbursement> login(Users users);

    //Retrieve reimbursement
    Reimbursement getReimbursementById(int reimbursement_id);

    //Retrieve all reimbursements
    List<Reimbursement> getAllReimbursement();

    String changeStatus(int reimbursement_id, Status status);


}
