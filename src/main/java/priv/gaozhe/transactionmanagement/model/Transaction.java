package priv.gaozhe.transactionmanagement.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class Transaction {
    private String id;
    @NotNull
    private String type;
    @NotNull
    private double amount;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(String type, double amount) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}