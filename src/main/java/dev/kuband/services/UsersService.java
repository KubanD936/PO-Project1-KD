package dev.kuband.services;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;

import java.util.ArrayList;
import java.util.List;

public interface UsersService {
    Users createUsers (Users users);

    /*public ArrayList<Reimbursement> login(Users users);*/

    boolean loginUsers(Users users);

//    String updateIsAdminPrivilege(Users users);

//    String updateUsers(Users users);


    //Users getUsersById(int user_id);

    //Users getUsersByUsername(String username);


}
