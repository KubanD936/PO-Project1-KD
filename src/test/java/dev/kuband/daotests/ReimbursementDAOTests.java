package dev.kuband.daotests;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.repositories.ReimbursementDAO;
import dev.kuband.repositories.ReimbursementDAOPostgres;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReimbursementDAOTests {

    static ReimbursementDAO reimbursementDAO = new ReimbursementDAOPostgres();

    @Test
    @Order(1)
    void create_book_test(){
        Reimbursement newReimbursement = new Reimbursement(0,100, "", "", "Misc", 0, 1, 0, Status.APPROVED);
        Reimbursement savedReimbursement = reimbursementDAO.createReimbursement(newReimbursement);
        Assertions.assertNotEquals(0,savedReimbursement.getReimbursement_id());
    }

    @Test
    @Order(2)
    void get_book_by_id_test(){
        Reimbursement gotReimbursement = reimbursementDAO.getReimbursementById(1);
        Assertions.assertEquals(100, gotReimbursement.getAmount());
    }

    @Test
    @Order(3)
    void update_reimbursement_test(){
        Reimbursement reimbursement = reimbursementDAO.getReimbursementById(1);
        Reimbursement reimbursement1 = new Reimbursement(reimbursement.getReimbursement_id(), 150, reimbursement.getSubmitted(), reimbursement.getResolved(), reimbursement.getDescription(), reimbursement.getReimbursement_submitter_user_id(), reimbursement.getReimbursement_resolved_by_user_id(), reimbursement.getReimbursement_type_id(), reimbursement.getStatus());
        reimbursementDAO.updateReimbursement(reimbursement1);
        Reimbursement updatedReimbursement = reimbursementDAO.getReimbursementById(reimbursement1.getReimbursement_id());
        Assertions.assertEquals(150, updatedReimbursement.getAmount());
    }

    @Test
    @Order(4)
    void delete_reimbursement_by_id_test(){
        boolean result = reimbursementDAO.deleteReimbursementById(1);
        Assertions.assertTrue(result);
    }

}
