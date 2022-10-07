package com.revature.controller;

import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import com.revature.repository.UserRepository;
import com.revature.service.AuthService;
import io.javalin.Javalin;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class AuthController {

    private AuthService authService = new AuthService();
    private UserRepository uRepo = new UserRepository();

        //when you type login in postman, this is what it does
    public void mapEndpoints(Javalin app) {
        app.post("/login", (ctx) -> {
            User credentials = ctx.bodyAsClass(User.class);

            try {
                User user = authService.login(credentials.getUsername(), credentials.getPassword());
                ctx.result("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
                HttpSession session = ctx.req.getSession();
                session.setAttribute("user", user);
            } catch (InvalidLoginException e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });
        //when you send logout into postman, this is what it does
        app.post("/logout", (ctx) -> {
            ctx.req.getSession().invalidate();
            ctx.result("Logout Successful");
                });
        app.post("/register", (ctx) -> {
            User RegInfo = ctx.bodyAsClass(User.class);
            try {authService.register(RegInfo);
                ctx.result("User Account Created!");

            } catch (SQLException e) {
                ctx.status(400);
                ctx.result("That username is taken, please try another!");
            }


        });

    }
}
