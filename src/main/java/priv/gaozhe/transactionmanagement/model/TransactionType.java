package priv.gaozhe.transactionmanagement.model;

public enum TransactionType {
    DEPOSIT("DEPOSIT", "存款"),
    WITHDRAWAL("WITHDRAWAL", "取款"),
    TRANSFER("TRANSFER", "转账"),
    PAYMENT("PAYMENT","支付");

    private final String type;
    private final String name;

    TransactionType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}