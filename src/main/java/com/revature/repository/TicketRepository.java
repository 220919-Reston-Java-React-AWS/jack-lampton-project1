package com.revature.repository;

import com.revature.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import com.revature.model.User;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class TicketRepository {

  /*  public String SubmitTicket(double amount, String reason, String status, int id) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "INSERT INTO tickets (amount, reason, status, employee_id) VALUES (?, ?, null, ?)";


            PreparedStatement stmt = connectionObject.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setString(2, reason);
            stmt.setInt(4, id);
            stmt.executeUpdate();


        }
        return "Ticket added";
    }
    */
    public Ticket SubmitTicket(Ticket ticket) throws SQLException  {

        try(Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "INSERT INTO tickets (amount, reason, status, employee_id) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, ticket.getAmount());
            pstmt.setString(2, ticket.getReason());
            pstmt.setString(3, "pending");
            pstmt.setInt(4, ticket.getEmployeeId());


            int numberOfRecordsAdded = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int id = rs.getInt(1);

            return new Ticket(id, ticket.getAmount(), ticket.getReason(), "pending", ticket.getEmployeeId());
        }
    }
    public List<Ticket> getAllPendingTickets() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {


            List<Ticket> tickets = new ArrayList<>();
            String sql = "SELECT * FROM tickets WHERE status = 'pending'";

            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String reason = rs.getString("reason");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");

                Ticket newTicket = new Ticket(id, amount, reason, status, employeeId);

                tickets.add(newTicket);
            }

            return tickets;
        }
    }
    public List<Ticket> getAllTickets() throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {


            List<Ticket> tickets = new ArrayList<>();
            String sql = "SELECT * FROM tickets";

            Statement stmt = connectionObject.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String reason = rs.getString("reason");
                String status = rs.getString("status");
                int employeeId = rs.getInt("employee_id");

                Ticket newTicket = new Ticket(id, amount, reason, status, employeeId);

                tickets.add(newTicket);
            }

            return tickets;
        }
    }

    public List<Ticket> getAllOfAnIndividualsTickets(int employeeId) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {

            List<Ticket> tickets = new ArrayList<>();

            String sql = "Select * FROM tickets WHERE employee_id = ?";

            PreparedStatement stmt = connectionObject.prepareStatement(sql);

            stmt.setInt(1, employeeId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String reason = rs.getString("reason");
                String status = rs.getString("status");
                //use eId since employeeId is already taken
                int eId = rs.getInt("employee_id");

                Ticket newTicket = new Ticket(id, amount, reason, status, eId);

                tickets.add(newTicket);
            }

            return tickets;
        }
    }
            //method to approve or deny tickets
        public boolean approveOrDeny(int id, String status) throws SQLException {
            try(Connection connectionObj = ConnectionFactory.createConnection()) {
                String sql = "UPDATE tickets SET status = ? WHERE id = ?";

                PreparedStatement stmt = connectionObj.prepareStatement(sql);
                stmt.setString(1, status);
                stmt.setInt(2, id);

                int numRecordsUpdated = stmt.executeUpdate();

                return numRecordsUpdated == 1;
            }
        }

        public Ticket getTicketById(int id) throws SQLException {
            try (Connection connectionObj = ConnectionFactory.createConnection()) {
                String sql = "SELECT * FROM tickets WHERE id = ?";

                PreparedStatement stmt = connectionObj.prepareStatement(sql);

                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int tId = rs.getInt("id");
                    double amount = rs.getDouble("amount");
                    String reason = rs.getString("reason");
                    String status = rs.getString("status");
                    int employeeId = rs.getInt("employee_id");

                    return new Ticket(tId, amount, reason, status, employeeId);
                } else {
                    return null;
                }
            }
        }
}
