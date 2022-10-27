package dev.kuband.repositories;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;

import java.util.ArrayList;
import java.util.List;

public interface UsersDAO {

    Users createUsers(Users users);

    Users getUsersById(int user_id);

    Users getUsersByUsername(String username);

    List<Users> getAllUsers();

    ArrayList<Reimbursement> login(Users users);

    String updateUsers(Users users);

    String updateAdminPrivilege(Users users);
}
