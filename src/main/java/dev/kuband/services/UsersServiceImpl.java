package dev.kuband.services;

import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;
import dev.kuband.repositories.UsersDAO;

import java.util.ArrayList;
import java.util.List;

public class UsersServiceImpl implements  UsersService{

    private UsersDAO usersDAO;

    public UsersServiceImpl(UsersDAO usersDAO){
        this.usersDAO = usersDAO;
    }

    @Override
    public Users createUsers(Users users) {
        if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password field can't be empty");
        }
        Users savedUsers = this.usersDAO.createUsers(users);
        return savedUsers;
    }

    @Override
    public ArrayList<Reimbursement> login(Users users) {
        if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password field can't be empty");
        } else {
            ArrayList<Reimbursement> savedUsers = this.usersDAO.login(users);
            return savedUsers;
        }
    }

    @Override
    public String updateIsAdminPrivilege(Users users) {
        if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password field can't be empty");
        } else {
            String savedUsers = this.usersDAO.updateAdminPrivilege(users);
            return savedUsers;
        }
    }

    @Override
    public String updateUsers(Users users) {
        String savedReimbursement = this.usersDAO.updateUsers(users);
        return savedReimbursement;
    }
}
        /*if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password fields can't be empty");
        }
        if(this.usersDAO.getUsersByUsername(users.getUsername()).getUsername().length() == 0){
            Users savedUsers = this.usersDAO.createUsers(users);
            //return this.usersDAO.updateUsers(users);
            return savedUsers;
        }
        else{
            throw new RuntimeException("That Username already exists");
        }

    }*/
