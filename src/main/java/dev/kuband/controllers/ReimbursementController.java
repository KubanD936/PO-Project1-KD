package dev.kuband.controllers;

import com.google.gson.Gson;
import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import io.javalin.http.Handler;

import java.util.List;

public class ReimbursementController {

    public Handler createReimbursement = (ctx)->{
        String json =ctx.body();
        Gson gson = new Gson();
        Reimbursement reimbursement = gson.fromJson(json, Reimbursement.class);
        Reimbursement registeredReimbursement = Driver.reimbursementService.createReimbursement(reimbursement);
        String reimbursementJson = gson.toJson(registeredReimbursement);
        ctx.status(201);
        ctx.result(reimbursementJson);
    };

    public Handler getReimbursementByIdHandler = (ctx) ->{
        int reimbursement_id = Integer.parseInt(ctx.pathParam("reimbursement_id"));
        Reimbursement reimbursement = Driver.reimbursementService.getReimbursementById(reimbursement_id);
        Gson gson = new Gson();
        String json = gson.toJson(reimbursement);
        ctx.result(json);
    };

    public Handler getAllReimbursement = (ctx) ->{
        List<Reimbursement> reimbursements = Driver.reimbursementService.getAllReimbursement();
        Gson gson = new Gson();
        String json = gson.toJson(reimbursements);
        ctx.result(json);
    };

    public Handler updateReimbursementHandler = (ctx) -> {
        String reimbursementJSON = ctx.body();
        Gson gson = new Gson();
        Reimbursement reimbursement = gson.fromJson(reimbursementJSON, Reimbursement.class);
        Reimbursement updateReimbursement = Driver.reimbursementService.updateReimbursement(reimbursement);
        String json = gson.toJson(updateReimbursement);
        ctx.result(json);
    };

    public Handler deleteReimbursementHandler = (ctx) -> {
        int reimbursement_id = Integer.parseInt(ctx.pathParam("reimbursement_id"));
        boolean result = Driver.reimbursementService.deleteReimbursementById(reimbursement_id);
        if(result){
            ctx.status(204);
        }
        else {
            ctx.status(400);
            ctx.result("Could not process your delete request");
        }
    };
}