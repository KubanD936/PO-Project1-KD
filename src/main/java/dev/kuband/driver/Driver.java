package dev.kuband.driver;


import dev.kuband.controllers.ReimbursementController;
import dev.kuband.repositories.ReimbursementDAOPostgres;
import dev.kuband.repositories.UsersDAOPostgres;
import dev.kuband.services.ReimbursementService;
import dev.kuband.services.ReimbursementServiceImpl;
import dev.kuband.services.UsersService;
import dev.kuband.services.UsersServiceImpl;
import io.javalin.Javalin;

public class Driver {

    public static ReimbursementService reimbursementService = new ReimbursementServiceImpl(new ReimbursementDAOPostgres());
    public static UsersService usersService = new UsersServiceImpl(new UsersDAOPostgres());
    public static void main (String[] args){
        Javalin app = Javalin.create();

        ReimbursementController reimbursementController = new ReimbursementController();

        app.get("books/{reimbursement_id}", reimbursementController.getReimbursementByIdHandler);

        app.get("books", reimbursementController.getAllReimbursement);

        app.get("books", reimbursementController.createReimbursement);

        app.get("books", reimbursementController.updateReimbursementHandler);

        app.get("books/{reimbursement_id}", reimbursementController.deleteReimbursementHandler);

        app.start();
    }
}
