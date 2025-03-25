package priv.gaozhe.transactionmanagement.model;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private String id;
    @NotNull
    private TransactionType type; // 修改为枚举类型
    @NotNull
    private double amount;
    // 新增属性：交易时间
    private Date transactionTime;
    // 新增属性：交易描述
    private String description;
    // 新增属性：交易来源账户
    private String sourceAccount;
    // 新增属性：交易目标账户
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