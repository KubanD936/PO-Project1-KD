package dev.kuband.services;

import dev.kuband.entities.Users;
import dev.kuband.repositories.UsersDAO;

import java.util.List;

public class UsersServiceImpl implements  UsersService{

    private UsersDAO usersDAO;

    public UsersServiceImpl(UsersDAO usersDAO){
        this.usersDAO = usersDAO;
    }

    @Override
    public Users createUsers(Users users) {
        if(users.getUsername() == null && users.getPassword() == null){
            throw new RuntimeException("Username and Password field can't be empty");
        }
        Users savedUsers = this.usersDAO.createUsers(users);
        return savedUsers;
    }

    @Override
    public Users getUsersbyId(int user_id) {
        return this.usersDAO.getUsersById(user_id);
    }

    @Override
    public List<Users> getAllUsers() {
        return this.usersDAO.getAllUsers();
    }

    @Override
    public Users updateUsers(Users users) {
        if(users.getUsername() == null && users.getPassword() == null){
            throw new RuntimeException("Username and Password field can't be empty");
        }
        Users savedUsers = this.usersDAO.createUsers(users);
        return this.usersDAO.updateUsers(users);
    }

    @Override
    public boolean deleteUsersById(int user_id) {
        return this.usersDAO.deleteUsersById(user_id);
    }
}
