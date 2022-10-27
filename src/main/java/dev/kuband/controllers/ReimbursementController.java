package dev.kuband.controllers;

import com.google.gson.Gson;
import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import dev.kuband.repositories.ReimbursementDAOPostgres;
import io.javalin.http.Handler;

import java.util.List;

public class ReimbursementController {


    //Create try catches, so that catch can throw custom exception
    //Create has 3 exceptions
    //Exception doesn't have an amount and description
    public Handler createReimbursement = (ctx)->{
        String json =ctx.body();
        Gson gson = new Gson();
        Reimbursement reimbursement = gson.fromJson(json, Reimbursement.class);
        Reimbursement registeredReimbursement = Driver.reimbursementService.createReimbursement(reimbursement);
        if (registeredReimbursement == null){
            ctx.status(400);
            ctx.result("Amount, Description, and Type can't be empty");
        } else {
            ctx.status(201);
            ctx.result("Reimbursement request "+registeredReimbursement.getReimbursement_id()+ " has been registered!");
        }
    };

    public Handler getReimbursementByIdHandler = (ctx) ->{
        int reimbursement_id = Integer.parseInt(ctx.pathParam("reimbursement_id"));
        Reimbursement reimbursement = Driver.reimbursementService.getReimbursementById(reimbursement_id);
        Gson gson = new Gson();
        String json = gson.toJson(reimbursement);
        ctx.result(json);
    };

    //View tickets
    public Handler getAllReimbursements = (ctx) -> {
        /*String json = ctx.body();
        Gson gson = new Gson();*/
        ReimbursementDAOPostgres reimbursementDAOPostgres = new ReimbursementDAOPostgres();
        String reimbursements = String.valueOf(reimbursementDAOPostgres.getAllReimbursement());
        if (reimbursements.equals("Not logged in!")){
            ctx.status(401);
            ctx.result("Not logged in");
        } else{
            ctx.status(200);
            ctx.result("Displaying all tickets");
        }
        ctx.result(reimbursements);
    };

/*
    public Handler getPendingReimbursements = (ctx) ->{
        try{
            String json = ctx.body();
            Gson gson = new Gson();
            ReimbursementDAOPostgres reimbursementDAOPostgres = new ReimbursementDAOPostgres();
            List<Reimbursement> reimbursements = Driver.reimbursementService.getPendingReimbursement();
        }
        catch(){

        }

    };*/

    public Handler updateReimbursementHandler = (ctx) -> {
        String reimbursementJSON = ctx.body();
        Gson gson = new Gson();
        Reimbursement reimbursement = gson.fromJson(reimbursementJSON, Reimbursement.class);
        ReimbursementDAOPostgres reimbursementDAOPostgres = new ReimbursementDAOPostgres();
        String updateReimbursement = Driver.reimbursementService.changeReimbursementStatus(reimbursement.getReimbursement_id(), reimbursement.getStatus());
        switch (updateReimbursement){
            case"Reimbursement already approved and cannot be changed.":
            case"Reimbursement already denied and cannot be changed.":
                ctx.status(401);
                break;
            case "Change failed\r\nVerify reimbursement request id and status are correct!":
                ctx.status(400);
                break;
            default:
                ctx.status(202);
        }
        ctx.result(updateReimbursement.toString());
    };
}
