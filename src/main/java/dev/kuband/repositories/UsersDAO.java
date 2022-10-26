package dev.kuband.repositories;

import dev.kuband.entities.Users;

import java.util.List;

public interface UsersDAO {

    Users createUsers(Users users);

    Users getUsersById(int user_id);

    List<Users> getAllUsers();

    Users updateUsers(Users users);

    boolean deleteUsersById(int user_id);
}
