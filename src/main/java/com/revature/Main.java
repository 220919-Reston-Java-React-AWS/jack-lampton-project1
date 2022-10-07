package com.revature;

import com.revature.controller.AuthController;
import com.revature.controller.TicketController;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        AuthController ac = new AuthController();
        ac.mapEndpoints(app);

        TicketController tc = new TicketController();
        tc.mapEndpoints(app);

        app.start(8080);
    }
}
