package chapter3;

public class Account {
    private final String accountId;
    private final int creditRating;
    private double balance;


    public Account() {
        this.accountId = "default";
        creditRating = 700;
        balance = 0;
    }

    public Account(String accountId) {
        this.accountId = accountId;
        creditRating = 700;
        balance = 0;
    }

    public Account(String accountId, int creditRating, double balance) {
        this.accountId = accountId;
        this.creditRating = creditRating;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public int getCreditRating() {
        return creditRating;
    }
}