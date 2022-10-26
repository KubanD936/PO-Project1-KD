package dev.kuband.services;

import dev.kuband.entities.Users;

import java.util.List;

public interface UsersService {
    Users createUsers (Users users);

    Users getUsersbyId(int user_id);

    List<Users> getAllUsers();

    Users updateUsers(Users users);

    boolean deleteUsersById(int user_id);
}
