package com.revature.model;
import java.util.Objects;


public class Ticket {
    private int id;
    private double amount;
    private String reason;

    private String status;

    private int employeeId;

    public Ticket (){};

    public Ticket(int id, double amount, String reason, String status, int employeeId) {
        this.id = id;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.employeeId = employeeId;
    }

    public int getId() {return id; }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setId(int id) {
        this.id = id; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", employeeId=" + employeeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(ticket.amount, amount) == 0 && employeeId == ticket.employeeId && reason.equals(ticket.reason) && status.equals(ticket.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, reason, status, employeeId);
    }
}

