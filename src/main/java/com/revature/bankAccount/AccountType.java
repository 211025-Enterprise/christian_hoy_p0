package com.revature.bankAccount;

public enum AccountType {

    Checkings("Checkings"),
    Savings("Savings");

    public final String value;

    AccountType(String value){
        this.value = value;
    }
}