package com.revature.controller;

import com.revature.exception.InvalidLoginException;
import com.revature.exception.NonexistentTicketException;
import com.revature.exception.TicketAlreadyProcessedException;
import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.repository.TicketRepository;
import com.revature.service.TicketService;
import io.javalin.Javalin;


import javax.servlet.http.HttpSession;
import java.util.List;

public class TicketController {


    private TicketService ticketService = new TicketService();
    private TicketRepository tRepo = new TicketRepository();
    public void mapEndpoints(Javalin app) {
        app.get("/ticketqueue", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) {
                if (user.getRoleId() == 2) {
                    List<Ticket> tickets = ticketService.getAllPendingTickets();

                    ctx.json(tickets);
                } else if (user.getRoleId() == 1) {
                    ctx.result("User lacks authorization, must be a manager to view the ticket queue.");
                    ctx.status(401);
                } else {
                    ctx.result("User is logged in, but is neither an employee nor a manager.");
                    ctx.status(401);
                }
            } else {
                ctx.result("User is not logged in.");
                ctx.status(401);
            }
        });
        app.get("/tickets", (ctx) -> {

            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");

            if (user != null) {
                if (user.getRoleId() == 2) {
                    List<Ticket> tickets = ticketService.getAllTickets();

                    ctx.json(tickets);
                } else if (user.getRoleId() == 1) {
                    int employeeId = user.getId();
                    List<Ticket> tickets = ticketService.getAllOfAnIndividualsTickets(employeeId);

                    ctx.json(tickets);
                } else {
                    ctx.result("User is logged in, but is neither an employee nor a manager");
                    ctx.status(401);
                }
            } else {
                ctx.result("User is not logged in");
                ctx.status(401);
            }
        });

        app.post("/submit", (ctx) -> {
            Ticket RegInfo = ctx.bodyAsClass(Ticket.class);
            try {
                ticketService.submit(RegInfo);
                ctx.result("Ticket Submitted.");
            } catch (IllegalArgumentException e) {
                ctx.status(400);
                ctx.result(e.getMessage());
            }


        });

        app.patch("/process/{ticketId}", (ctx) -> {
            HttpSession httpSession = ctx.req.getSession();
            User user = (User) httpSession.getAttribute("user");
            if (user != null) {
                if (user.getRoleId() == 2) {
                    int ticketId = Integer.parseInt(ctx.pathParam("ticketId"));

                    Ticket a = ctx.bodyAsClass(Ticket.class);
                    String status = a.getStatus();

                    try {
                        ticketService.approveOrDeny(ticketId, status);

                        ctx.result("Ticket Processed.");
                    } catch (TicketAlreadyProcessedException | IllegalArgumentException e) {
                        ctx.result(e.getMessage());
                        ctx.status(400);
                    } catch (NonexistentTicketException e) {
                        ctx.result(e.getMessage());
                        ctx.status(404);
                    }
                } else {
                    ctx.result("User lacks proper authorization.");
                    ctx.status(401);
                }
            } else {
                ctx.result("User is not logged in.");
                ctx.status(401);
            }


        });
    }


}
