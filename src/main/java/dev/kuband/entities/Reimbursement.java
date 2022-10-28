package dev.kuband.entities;

import java.util.Objects;

public class Reimbursement {
    //Reimbursement parameters
    private int reimbursement_id;

    private String user;
    private int amount;
    private String description;
    private Status status = Status.PENDING;

    public Reimbursement() {
    }

    public Reimbursement(int reimbursement_id, String user, int amount, String description, Status status) {
        this.reimbursement_id = reimbursement_id;
        this.user = user;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursement_id=" + reimbursement_id +
                ", user='" + user + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status=" + status.name() +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Reimbursement that = (Reimbursement) o;
//        return reimbursement_id == that.reimbursement_id && amount == that.amount && Objects.equals(user, that.user) && Objects.equals(description, that.description) && status == that.status;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(reimbursement_id, user, amount, description, status);
//    }

    public int getReimbursement_id() {
        return reimbursement_id;
    }

    public void setReimbursement_id(int reimbursement_id) {
        this.reimbursement_id = reimbursement_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

