package dev.kuband.entities;

import java.util.Objects;

public class Reimbursement {
    //Reimbursement parameters
    private int reimbursement_id;
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private int reimbursement_submitter_user_id;
    private int reimbursement_resolved_by_user_id;
    private int reimbursement_type_id;
    private Status status;

    public Reimbursement() {
    }

    public Reimbursement(int reimbursement_id, int amount, String submitted, String resolved, String description, int reimbursement_submitter_user_id, int reimbursement_resolved_by_user_id, int reimbursement_type_id, Status status) {
        this.reimbursement_id = reimbursement_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.reimbursement_submitter_user_id = reimbursement_submitter_user_id;
        this.reimbursement_resolved_by_user_id = reimbursement_resolved_by_user_id;
        this.reimbursement_type_id = reimbursement_type_id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursement_id=" + reimbursement_id +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", reimbursement_submitter_user_id=" + reimbursement_submitter_user_id +
                ", reimbursement_resolved_by_user_id=" + reimbursement_resolved_by_user_id +
                ", reimbursement_type_id=" + reimbursement_type_id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return reimbursement_id == that.reimbursement_id && amount == that.amount && reimbursement_submitter_user_id == that.reimbursement_submitter_user_id && reimbursement_resolved_by_user_id == that.reimbursement_resolved_by_user_id && reimbursement_type_id == that.reimbursement_type_id && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbursement_id, amount, submitted, resolved, description, reimbursement_submitter_user_id, reimbursement_resolved_by_user_id, reimbursement_type_id, status);
    }

    public int getReimbursement_id() {
        return reimbursement_id;
    }

    public void setReimbursement_id(int reimbursement_id) {
        this.reimbursement_id = reimbursement_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReimbursement_submitter_user_id() {
        return reimbursement_submitter_user_id;
    }

    public void setReimbursement_submitter_user_id(int reimbursement_submitter_user_id) {
        this.reimbursement_submitter_user_id = reimbursement_submitter_user_id;
    }

    public int getReimbursement_resolved_by_user_id() {
        return reimbursement_resolved_by_user_id;
    }

    public void setReimbursement_resolved_by_user_id(int reimbursement_resolved_by_user_id) {
        this.reimbursement_resolved_by_user_id = reimbursement_resolved_by_user_id;
    }

    public int getReimbursement_type_id() {
        return reimbursement_type_id;
    }

    public void setReimbursement_type_id(int reimbursement_type_id) {
        this.reimbursement_type_id = reimbursement_type_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
