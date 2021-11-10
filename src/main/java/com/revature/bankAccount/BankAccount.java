package com.revature.bankAccount;
import java.io.Serializable;

public class BankAccount implements Serializable {

    private int accountId;
    private int userId;
    private String accountName;
    private double balance;
    private AccountType accountType;

    public BankAccount(){
    }

    public BankAccount(int userId, String accountName, double balance, AccountType accountType) {
        this.userId = userId;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public BankAccount(int userId, int accountId, String accountName, double balance, AccountType accountType) {
        this.userId = userId;
        this.accountId = accountId;
        this.accountName = accountName;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String toString() {
        return "BankAccount{" +
                "accountId=" + accountId +
                ", userId='" + userId + '\'' +
                ", accountName='" + accountName + '\'' +
                ", balance='" + balance + '\'' +
                ", accountType=" + accountType +
                '}';
    }
}