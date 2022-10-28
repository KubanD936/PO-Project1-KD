package dev.kuband.driver;


import dev.kuband.controllers.ReimbursementController;
import dev.kuband.controllers.UsersController;
import dev.kuband.entities.Users;
/*import dev.kuband.repositories.ReimbursementDAOPostgres;*/
/*import dev.kuband.repositories.UsersDAOPostgres;*/
/*import dev.kuband.repositories.ReimbursementDAO;*/
import dev.kuband.repositories.UsersAndReimbursementDAOPostgres;
import dev.kuband.services.ReimbursementService;
import dev.kuband.services.ReimbursementServiceImpl;
import dev.kuband.services.UsersService;
import dev.kuband.services.UsersServiceImpl;
import dev.kuband.repositories.UsersAndReimbursementDAO;
import io.javalin.Javalin;

public class Driver {

    /*public static Users currentLoggedUsers;*/
    /*public static ReimbursementService reimbursementService = new ReimbursementServiceImpl(new ReimbursementDAOPostgres());
    public static UsersService usersService = new UsersServiceImpl(new UsersDAOPostgres());*/

    public static Users currentUsers;
    public static UsersService usersService = new UsersServiceImpl(new UsersAndReimbursementDAOPostgres());
    public static ReimbursementService reimbursementService = new ReimbursementServiceImpl(new UsersAndReimbursementDAOPostgres());


    public static void main (String[] args){
        Javalin app = Javalin.create();

        ReimbursementController reimbursementController = new ReimbursementController();
        UsersController usersController = new UsersController();

        //Reimbursements

        app.post("/createReimbursement", reimbursementController.createReimbursement);

        app.get("/getReimbursements", reimbursementController.getAllReimbursements);

        app.post("/updateReimbursements", reimbursementController.updateReimbursementHandler);

        app.post("/changeStatus", reimbursementController.updateReimbursementHandler);

        app.get("/getAllTheseUsers", reimbursementController.getAllThisUserReimbursements);

        /*app.get("reimbursements/{reimbursement_id}", reimbursementController.getReimbursementByIdHandler);*/



        //Users

        app.post("/createUser", usersController.createUser);

        app.post("/login", usersController.loginUsers);

        app.post("/updateUserToAdmin", usersController.updateIsAdminHandler);

        app.post("/updateUsers", usersController.updateUsersHandler);

        app.start();
    }
}
