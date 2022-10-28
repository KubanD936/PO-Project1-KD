package dev.kuband.daotests;

/*import dev.kuband.repositories.ReimbursementDAO;
import dev.kuband.repositories.ReimbursementDAOPostgres;*/
import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;
import dev.kuband.repositories.UsersAndReimbursementDAO;
import dev.kuband.repositories.UsersAndReimbursementDAOPostgres;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersAndReimbursementDAOPostgresTests {

    UsersAndReimbursementDAO usersAndReimbursementDAO = new UsersAndReimbursementDAOPostgres();
    public Users fakeUser = new Users(9, "Patrick", "pass", true);

    @Test
    @Order(1)
    void create_new_reimbursement_test(){
        Reimbursement newReimbursement = new Reimbursement(0, "Patrick", 100, "Hotel", Status.PENDING);
        Reimbursement savedReimbursement = usersAndReimbursementDAO.createReimbursement(newReimbursement);
        System.out.println(savedReimbursement);
        Assertions.assertNotNull(savedReimbursement);
        Assertions.assertNotEquals(0, savedReimbursement.getDescription());
    }

    @Test
    @Order(2)
    void get_reimbursement_by_id_test(){
        Reimbursement reimbursement = usersAndReimbursementDAO.getReimbursementById(1);
        Assertions.assertNotNull(reimbursement);
    }

    @Test
    @Order(3)
    void get_all_reimbursement_test(){
        List<Reimbursement> reimbursementList = usersAndReimbursementDAO.getAllReimbursement();
        Assertions.assertTrue(reimbursementList.size() > 0);
        System.out.println(reimbursementList);
    }

    @Test
    @Order(4)
    void get_all_reimbursement_by_current_user_test(){
//        Users users = new Users();
//        users.setUser_id(11);
        List<Reimbursement> reimbursementList = usersAndReimbursementDAO.getAllReimbursementByUsers(fakeUser);
        Assertions.assertTrue(reimbursementList.size() > 0);
        System.out.println(reimbursementList);
    }

    @Test
    @Order(5)
    void change_status_test(){
        Reimbursement newReimbursement = new Reimbursement(0, "Patrick", 100, "Hotel", Status.PENDING);
        Reimbursement savedReimbursement = usersAndReimbursementDAO.createReimbursement(newReimbursement);
        String changedReimbursement = usersAndReimbursementDAO.changeStatus(savedReimbursement.getReimbursement_id(), Status.APPROVED);
        System.out.println(changedReimbursement);
        Reimbursement reimbursement = usersAndReimbursementDAO.getReimbursementById(savedReimbursement.getReimbursement_id());
        Assertions.assertEquals(Status.APPROVED, reimbursement.getStatus());
        System.out.println(reimbursement.getStatus());
    }

    @Test
    @Order(6)
    void create_new_user_test(){
        fakeUser = new Users(0, "Delion1", "pass", true);
        Users createdUser = usersAndReimbursementDAO.createUsers(fakeUser);
        Assertions.assertNotNull(fakeUser);
    }

    @Test
    @Order(7)
    void login_user_test(){
        Boolean returnValue = usersAndReimbursementDAO.loginUsers(fakeUser);
        Assertions.assertTrue(returnValue);
    }

//    @Test
//    @Order(8)
//    void update_user_test(){
//        String updateUsers = usersAndReimbursementDAO.updateUsers(fakeUser);
//        System.out.println(updateUsers);
//        Assertions.assertTrue(updateUsers.contains("success"));
//    }
    /*static ReimbursementDAO reimbursementDAO = new ReimbursementDAOPostgres();

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
    }*/

}
