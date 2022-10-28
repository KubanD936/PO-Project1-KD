package dev.kuband.services;

import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
/*import dev.kuband.repositories.ReimbursementDAO;*/
import dev.kuband.repositories.UsersAndReimbursementDAO;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService{

    /*private ReimbursementDAO reimbursementDAO;*/
    /*public ReimbursementServiceImpl(ReimbursementDAO reimbursementDAO){
        this.reimbursementDAO=reimbursementDAO;
    }*/
    private UsersAndReimbursementDAO usersAndReimbursementDAO;
    public ReimbursementServiceImpl(UsersAndReimbursementDAO usersAndReimbursementDAO) {
        this.usersAndReimbursementDAO = usersAndReimbursementDAO;
    }

    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {

        if (reimbursement.getDescription().length() == 0 || reimbursement.getAmount() <= 0) {
            throw new RuntimeException("description and amount cannot be empty");
        } else {
            Reimbursement savedReimbursement = this.usersAndReimbursementDAO.createReimbursement(reimbursement);
            return savedReimbursement;
        }
    }

    @Override
    public String changeReimbursementStatus(int reimbursement_id, Status status) {
        if (reimbursement_id <= 0) {
            throw new RuntimeException("Reimbursement request id cannot be 0 or lower.");
        } else if (status.equals(Status.PENDING)) {
            throw new RuntimeException("Cannot change a reimbursement request to pending.");
        } else {
            String savedReimbursement = this.usersAndReimbursementDAO.changeStatus(reimbursement_id, status);
            return savedReimbursement;
        }
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        return this.usersAndReimbursementDAO.getAllReimbursement();
    }

/*    @Override
    public List<Reimbursement> getPendingReimbursement() {
        if(!Driver.currentLoggedUsers.isAdmin() || Driver.currentLoggedUsers == null){
            throw new RuntimeException("Log in as an admin to do that") ;
        }
            return this.reimbursementDAO.getPendingReimbursement();
    }*/

    @Override
    public Reimbursement getReimbursementById(int reimbursement_id) {
        return this.usersAndReimbursementDAO.getReimbursementById(reimbursement_id);
    }

}
