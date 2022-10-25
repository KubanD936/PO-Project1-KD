package dev.kuband.services;

import dev.kuband.entities.Reimbursement;
import dev.kuband.repositories.ReimbursementDAO;

import java.util.List;

public class ReimbursementServiceImpl implements ReimbursementService{

    private ReimbursementDAO reimbursementDAO;

    public ReimbursementServiceImpl(ReimbursementDAO reimbursementDAO){
        this.reimbursementDAO=reimbursementDAO;
    }

    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        if(reimbursement.getAmount() == 0){
            throw new RuntimeException("Reimbursement amount can't be 0");
        }
        Reimbursement savedReimbursement =this.reimbursementDAO.createReimbursement(reimbursement);
        return savedReimbursement;
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursement_id) {
        return this.reimbursementDAO.getReimbursementById(reimbursement_id);
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        return this.reimbursementDAO.getAllReimbursement();
    }

    @Override
    public Reimbursement updateReimbursement(Reimbursement reimbursement) {
        if(reimbursement.getAmount() == 0){
            throw new RuntimeException("Reimbursement amount can't be 0");
        }
        Reimbursement savedReimbursement =this.reimbursementDAO.createReimbursement(reimbursement);
        return this.reimbursementDAO.updateReimbursement(reimbursement);
    }

    @Override
    public boolean deleteReimbursementById(int reimbursement_id) {
        return this.reimbursementDAO.deleteReimbursementById(reimbursement_id);
    }
}
