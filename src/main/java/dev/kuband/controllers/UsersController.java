package dev.kuband.controllers;

import com.google.gson.Gson;
import dev.kuband.driver.Driver;
import dev.kuband.entities.Users;
import io.javalin.http.Handler;

import java.util.List;

public class UsersController {

    public Handler createUser = (ctx) ->{
        String json = ctx.body();
        Gson gson = new Gson();
        Users users = gson.fromJson(json, Users.class);
        Users registeredUsers = Driver.usersService.createUsers(users);

        if(registeredUsers == null){
            ctx.status(400);
            ctx.result("Username already exists");
        }
        else {
            ctx.status(200);
            ctx.result("Your account has been created successfully");
        }
        String userJson = gson.toJson(registeredUsers);
        ctx.status(201);
        ctx.result(userJson);
    };

    public Handler getUsersByIdHandler = (ctx) ->{
        int user_id = Integer.parseInt(ctx.pathParam("user_id"));
        Users users = Driver.usersService.getUsersById(user_id);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };

    public Handler getUsersByUsername = (ctx) ->{
        String username = ctx.pathParam("username");
        Users users = Driver.usersService.getUsersByUsername(username);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };

    public Handler getAllUsers = (ctx) ->{
        List<Users> users = Driver.usersService.getAllUsers();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        ctx.result(json);
    };

    public Handler updateUsersHandler = (ctx) ->{
        String usersJson = ctx.body();
        Gson gson = new Gson();
        Users users = gson.fromJson(usersJson, Users.class);
        Users updateUsers = Driver.usersService.updateUsers(users);
        String json = gson.toJson(updateUsers);
        ctx.result(json);
    };

    public Handler deleteUsersHandler = (ctx) ->{
        int user_id = Integer.parseInt(ctx.pathParam("user_id"));
        boolean result = Driver.usersService.deleteUsersById(user_id);
        if(result){
            ctx.status(204);
        }
        else{
            ctx.status(400);
            ctx.result("Could not delete User");
        }
    };
}
