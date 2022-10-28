package dev.kuband.services;

import dev.kuband.entities.Users;
/*import dev.kuband.repositories.UsersDAO;*/
import dev.kuband.exceptions.InvlaidInputException;
import dev.kuband.repositories.UsersAndReimbursementDAO;

public class UsersServiceImpl implements  UsersService{

    /*private UsersDAO usersDAO;*/
    private UsersAndReimbursementDAO usersAndReimbursementDAO;

    /*public UsersServiceImpl(UsersDAO usersDAO){
        this.usersDAO = usersDAO;
    }*/
    public UsersServiceImpl(UsersAndReimbursementDAO usersAndReimbursementDAO){
        this.usersAndReimbursementDAO = usersAndReimbursementDAO;
    }

    @Override
    public Users createUsers(Users users) {
        System.out.println("this is from Service layer" + users);
        if(users.getUsername().length() == 0 || users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password field can't be empty");
        }
        Users savedUsers = this.usersAndReimbursementDAO.createUsers(users);
        return savedUsers;
    }

    @Override
    public boolean loginUsers(Users users){
        if(users.getUsername().length() == 0){
            throw new RuntimeException("Employee username must not be blank!");
        }
        if(users.getPassword().length() == 0){
            throw new RuntimeException("Employee password must not be blank!");
        }
        return this.usersAndReimbursementDAO.loginUsers(users);
    }

    /*@Override
    public ArrayList<Reimbursement> login(Users users) {
        *//*if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
            throw new RuntimeException("Username and Password field can't be empty");
        } else {
            ArrayList<Reimbursement> savedUsers = this.usersDAO.login(users);
            return savedUsers;
        }*//*

        if (users.getUsername().length() == 0) {
            throw new RuntimeException("username cannot be empty");
        } else if (users.getPassword().length() == 0) {
            throw new RuntimeException("password cannot be empty");
        } else {
            ArrayList<Reimbursement> savedUsers = this.usersAndReimbursementDAO.login(users);
            return savedUsers;
        }
    }*/

//    @Override
//    public String updateIsAdminPrivilege(Users users) {
//        if(users.getUsername().length() == 0 && users.getPassword().length() == 0){
//            throw new InvlaidInputException("Username and Password field can't be empty");
//        } else {
//            String savedUsers = this.usersAndReimbursementDAO.updateAdminPrivilege(users);
//            return savedUsers;
//        }
//    }

    @Override
    public String updateUsers(Users users) {
        String savedReimbursement = this.usersAndReimbursementDAO.updateUsers(users);
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
