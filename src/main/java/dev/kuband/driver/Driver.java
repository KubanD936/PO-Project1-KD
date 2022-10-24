package dev.kuband.driver;

import dev.kuband.repositories.ReimbursementDAOPostgres;
import dev.kuband.services.ReimbursementService;
import dev.kuband.services.ReimbursementServiceImpl;
import io.javalin.Javalin;

public class Driver {

    public static ReimbursementService reimbursementService = new ReimbursementServiceImpl(new ReimbursementDAOPostgres());
    public static void main (String[] args){
        Javalin app = Javalin.create();

        app.start();
    }
}
