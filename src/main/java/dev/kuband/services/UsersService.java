package dev.kuband.services;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;

import java.util.ArrayList;
import java.util.List;

public interface UsersService {
    Users createUsers (Users users);

    public ArrayList<Reimbursement> login(Users users);

    Users getUsersById(int user_id);

    Users getUsersByUsername(String username);

    List<Users> getAllUsers();

    Users updateUsers(Users users);

    boolean deleteUsersById(int user_id);
}
