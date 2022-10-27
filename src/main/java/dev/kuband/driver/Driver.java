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

        app.post("reimbursements", reimbursementController.createReimbursement);

        app.get("reimbursements/{reimbursement_id}", reimbursementController.getReimbursementByIdHandler);

        app.get("reimbursements", reimbursementController.getAllReimbursements);

        app.put("reimbursements", reimbursementController.updateReimbursementHandler);

        //Users

        app.post("users", usersController.createUser);

        app.get("users", usersController.logInHandler);

        app.put("users/isAdmin", usersController.updateIsAdminHandler);

        app.put("users", usersController.updateUsersHandler);

        app.start();
    }
}
