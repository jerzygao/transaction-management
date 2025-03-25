package priv.gaozhe.transactionmanagement.model;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

/**
 * 交易记录实体
 */
public class Transaction {
    @NotNull
    private String id;
    /**
     * 交易类型
     */
    @NotNull
    private TransactionType type;
    /**
     * 交易金额
     */
    @NotNull
    private double amount;
    /**
     * 交易时间
     */
    @NotNull
    private Date transactionTime;
    /**
     * 交易描述
     */
    private String description;
    /**
     * 交易来源账户
     */
    private String sourceAccount;
    /**
     * 交易目标账户
     */
    private String targetAccount;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
        this.transactionTime = new Date();
    }

    public Transaction(TransactionType type, double amount, String description, String sourceAccount, String targetAccount) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.transactionTime = new Date();
        this.description = description;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }
}