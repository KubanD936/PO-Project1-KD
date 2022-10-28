package dev.kuband.controllers;

import com.google.gson.Gson;
import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import dev.kuband.entities.Users;
/*import dev.kuband.repositories.UsersDAOPostgres;*/
import io.javalin.http.Handler;

import java.util.ArrayList;
import java.util.List;

public class UsersController {

    public Handler createUser = (ctx) -> {
        String json = ctx.body();
        Gson gson = new Gson();
        Users users = gson.fromJson(json, Users.class);
        /*System.out.println(users);*/
        /*UsersDAOPostgres usersDAOPostgres = new UsersDAOPostgres();*/
        Users newUsers = Driver.usersService.createUsers(users);
        System.out.println("this is from newUsers" + newUsers);

        if (newUsers == null) {
            ctx.status(400);
            ctx.result("Username already exists or has been entered incorrectly");
        } else {
            ctx.status(200);
            ctx.result("Account created successfully");
        }
        /*String userJson = gson.toJson(newUsers);
        ctx.status(201);
        ctx.result(userJson);*/
    };

    public Handler loginUsers = (ctx) ->{
        String usersJson = ctx.body();
        Gson gson = new Gson();
        Users users = gson.fromJson(usersJson, Users.class);
        boolean result = Driver.usersService.loginUsers(users);
        if(result){
            ctx.status(202);
            ctx.result("Successfully logged in");
        }
        else{
            ctx.status(400);
            ctx.result("Could not process your login request");
        }
    };

    /*public Handler login = (ctx) -> {
        String json = ctx.body();
        Gson gson = new Gson();
        Users users = gson.fromJson(json, Users.class);
        ArrayList<Reimbursement> newReimbursements = Driver.usersService.login(users);
        String jsonString = "";
        if (newReimbursements == null) {
            jsonString = "Invalid username or password";
            ctx.status(406);
            ctx.result(jsonString);
        } else if (newReimbursements.size() == 0) {
            ctx.status(404);
            ctx.result("UsersController No tickets");
        } else {
            for (int i = 0; i < newReimbursements.size(); i++) {
                jsonString += newReimbursements.get(i).toString() + "\n\r";
            }
            ctx.status(202);
            ctx.result(jsonString);
        }
    };*/

//    public Handler updateIsAdminHandler = (ctx) -> {
//        String json = ctx.body();
//        Gson gson = new Gson();
//        Users users = gson.fromJson(json, Users.class);
//        String usersString = Driver.usersService.updateIsAdminPrivilege(users);
//        switch (usersString) {
//            case "Change failed\r\nVerify user id":
//                ctx.status(304);
//                break;
//            case "Not logged in":
//            case "Can't modify own account":
//                ctx.status(401);
//                break;
//            default:
//                ctx.status(202);
//        }
//        ctx.result(usersString);
//    };

//    public Handler updateUsersHandler = (ctx) -> {
//        if (Driver.currentUsers == null) {
//            ctx.status(400);
//            ctx.result("Need to log in");
//        } else {
//            String usersJson = ctx.body();
//            Gson gson = new Gson();
//            Users users = gson.fromJson(usersJson, Users.class);
//            users.setUsername(Driver.currentUsers.getUsername());
//            users.setAdmin(Driver.currentUsers.isAdmin());
//            users.setUser_id(Driver.currentUsers.getUser_id());
//            if (users.getPassword() == null){
//                users.setPassword(Driver.currentUsers.getPassword());
//            }
//            String employeeString = Driver.usersService.updateUsers(users);
//            if (Driver.currentUsers == null){
//                ctx.status(400);
//                ctx.result("You are not logged in!");
//            } else{
//                ctx.status(201);
//                ctx.result(employeeString);
//            }
//        }
//    };
}

/*    public Handler getUsersByIdHandler = (ctx) ->{
        int user_id = Integer.parseInt(ctx.pathParam("user_id"));
        Users users = Driver.usersService.getUsersById(user_id);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };*/

/*    public Handler getUsersByUsername = (ctx) ->{
        String username = ctx.pathParam("username");
        Users users = Driver.usersService.getUsersByUsername(username);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };

    public Handler getAllUsers = (ctx) ->{
        List<Users> users = Driver.usersService.();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };*/


