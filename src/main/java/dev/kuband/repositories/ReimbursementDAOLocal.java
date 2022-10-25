package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReimbursementDAOLocal implements ReimbursementDAO{

    private Map<Integer,Reimbursement> reimbursementTable = new HashMap();
    private int idCount = 1;

    @Override
    public Reimbursement createReimbursement(Reimbursement reimbursement) {
        reimbursement.setReimbursement_id(idCount);
        idCount++;
        reimbursementTable.put(reimbursement.getReimbursement_id(), reimbursement);
        System.out.println(reimbursementTable.values());

        return reimbursement;
    }

    @Override
    public Reimbursement getReimbursementById(int reimbursement_id) {
        return reimbursementTable.get(reimbursement_id);
    }

    @Override
    public List<Reimbursement> getAllReimbursement() {
        return null;
    }

    @Override
    public Reimbursement updateReimbursement(Reimbursement reimbursement) {
        return reimbursementTable.put(reimbursement.getReimbursement_id(), reimbursement);
    }

    @Override
    public boolean deleteReimbursementById(int reimbursement_id) {
        Reimbursement reimbursement =reimbursementTable.remove(reimbursement_id);
        if(reimbursement == null){
            return false;
        }
        else {
            return true;
        }
    }
}
