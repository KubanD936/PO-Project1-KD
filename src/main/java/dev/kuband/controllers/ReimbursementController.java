package dev.kuband.controllers;

import com.google.gson.Gson;
import dev.kuband.driver.Driver;
import dev.kuband.entities.Reimbursement;
import io.javalin.http.Handler;

public class ReimbursementController {
    public Handler createReimbursement = (ctx)->{
        String json =ctx.body();
        Gson gson = new Gson();
        Reimbursement reimbursement = gson.fromJson(json, Reimbursement.class);
        Reimbursement registeredReimbursement = Driver.reimbursementService.createReimbursement(reimbursement);
    };
}
