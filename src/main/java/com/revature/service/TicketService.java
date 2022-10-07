package com.revature.service;

import com.revature.exception.NonexistentTicketException;
import com.revature.exception.TicketAlreadyProcessedException;
import com.revature.model.Ticket;
import com.revature.model.User;
import com.revature.repository.TicketRepository;
import org.eclipse.jetty.util.DateCache;

import java.sql.SQLException;
import java.util.List;

public class TicketService {

    private TicketRepository ticketRepository = new TicketRepository();

    //public void SubmitTicket(double amount, String reason, String status, int id) throws SQLException {

   // }
   public void submit(Ticket ticket) throws SQLException, IllegalArgumentException {

       ticketRepository.SubmitTicket(ticket);
        if (ticket.getAmount() <= 0) {
            throw new IllegalArgumentException("Ticket amount must be a dollar amount greater than zero.");
        }
        if (ticket.getReason() == null) {
            throw new IllegalArgumentException("Please include a description of the ticket");
        }
   }
    public List<Ticket> getAllPendingTickets() throws SQLException {
       return ticketRepository.getAllPendingTickets();
    }
    public List<Ticket> getAllTickets() throws SQLException {
        return ticketRepository.getAllTickets();
    }

    public List<Ticket> getAllOfAnIndividualsTickets(int employeeId) throws SQLException {
        return ticketRepository.getAllOfAnIndividualsTickets(employeeId);
    }
    public boolean approveOrDeny(int id, String status) throws SQLException, NonexistentTicketException, TicketAlreadyProcessedException {
        //manager tries to enter something other than "approved or denied"
        if (!(status.equals("approved")) && !(status.equals("denied"))) {
            throw new IllegalArgumentException("Please enter either 'approved' or 'denied'");
        }
        //ticket does not exist
        Ticket ticket = ticketRepository.getTicketById(id);
        if (ticket == null) {
            throw new NonexistentTicketException("Ticket of that ID not found");
        }

        //ticket has already been processed
        System.out.println(ticket.getStatus());
        if (!(ticket.getStatus().equals("pending"))) {
            throw new TicketAlreadyProcessedException("Ticket of that ID has already been processed");
        }
        return ticketRepository.approveOrDeny(id, status);
    }

}
