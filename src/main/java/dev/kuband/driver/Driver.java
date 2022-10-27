package dev.kuband.driver;


import dev.kuband.controllers.ReimbursementController;
import dev.kuband.controllers.UsersController;
import dev.kuband.entities.Users;
import dev.kuband.repositories.ReimbursementDAOPostgres;
import dev.kuband.repositories.UsersDAOPostgres;
import dev.kuband.services.ReimbursementService;
import dev.kuband.services.ReimbursementServiceImpl;
import dev.kuband.services.UsersService;
import dev.kuband.services.UsersServiceImpl;
import io.javalin.Javalin;

public class Driver {

    public static Users currentLoggedUsers;
    public static ReimbursementService reimbursementService = new ReimbursementServiceImpl(new ReimbursementDAOPostgres());
    public static UsersService usersService = new UsersServiceImpl(new UsersDAOPostgres());
    public static void main (String[] args){
        Javalin app = Javalin.create();

        ReimbursementController reimbursementController = new ReimbursementController();
        UsersController usersController = new UsersController();

        //Reimbursements

        app.get("books/{reimbursement_id}", reimbursementController.getReimbursementByIdHandler);

        app.get("books", reimbursementController.getAllReimbursement);

        app.post("books", reimbursementController.createReimbursement);

        app.put("books", reimbursementController.updateReimbursementHandler);

        app.delete("books/{reimbursement_id}", reimbursementController.deleteReimbursementHandler);

        //Users

        app.get("users/{users_id}", usersController.getUsersByIdHandler);

        app.get("users", usersController.getAllUsers);

        app.post("users", usersController.createUser);

        app.put("users", usersController.updateUsersHandler);

        app.delete("users/{users_id}", usersController.deleteUsersHandler);

        app.start();
    }
}
