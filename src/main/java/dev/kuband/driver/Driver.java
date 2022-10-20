package dev.kuband.driver;

import io.javalin.Javalin;

public class Driver {
    public static void main (String[] args){
        Javalin app = Javalin.create();

        app.start();
    }
}
