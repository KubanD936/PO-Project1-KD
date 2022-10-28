package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Status;
import dev.kuband.entities.Users;

import java.util.ArrayList;
import java.util.List;

public interface UsersAndReimbursementDAO {

    Reimbursement createReimbursement(Reimbursement reimbursement);

    //Retrieve reimbursement
    Reimbursement getReimbursementById(int reimbursement_id);

    //Retrieve all reimbursements
    List<Reimbursement> getAllReimbursement();

//    List<Reimbursement> getAllReimbursementByUsers();

    List<Reimbursement> getAllReimbursementByUsers(Users users);

    String changeStatus(int reimbursement_id, Status status);

    Users createUsers(Users users);

//    Users getUsersById(int user_id);

    /*Users getUsersByUsername(String username);*/

//    List<Users> getAllUsers();

    boolean loginUsers(Users users);

    /*ArrayList<Reimbursement> login(Users users);*/

    String updateUsers(Users users);

//    String updateAdminPrivilege(Users users);
}
