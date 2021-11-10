package com.revature.service;

import com.revature.bankAccount.AccountType;
import com.revature.user.User;
import com.revature.bankAccount.BankAccount;
import com.revature.database.UserDao;
import com.revature.database.AccountDao;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 UserService receives input from the user and sends it to the DAO.
 **/
public class UserService {

    private User currentUser;
    private final UserDao userDao;
    private final AccountDao accountDao;
    private final Scanner scanner;
    private final MenuService menuService;

    public UserService(Scanner scanner, MenuService menuService){
        this.scanner = scanner;
        this.menuService = menuService;
        this.userDao = new UserDao();
        this.accountDao = new AccountDao();
    }
    /**
     Collects a username and password from the user and passes it to
     the DAO to create an account in the database.
     **/
    public void createUser(){
        String[] credentials = usernameAndPasswordInput();

        if(!userExists(credentials[0])){
            userDao.create(new User(credentials[0], credentials[1]));
        } else{
            menuService.usernameAlreadyExists();
        }
    }
    /**
     Collects a username and password from the user and uses it to authenticate
     their login. If successful then stores the user's information in currentUser.
     **/
    public boolean login(){
        String[] credentials = usernameAndPasswordInput();
        User user = userDao.getByUsername(credentials[0]);
        if(user != null){
            if(user.getPassword().equals(credentials[1])){
                currentUser = user;
                return true;
            }
        }
        return false;
    }
    /**
     Logs the user out and removes the current user from currentUser.
     **/
    public void logout(){
        this.currentUser = null;
    }
    /**
     Returns a user object of the currently logged in user.
     **/
    public User getCurrentUser(){
        return currentUser;
    }
    /**
     Sends a request to the DOA with a username to check if it exists in the database.
     **/
    private boolean userExists(String username){
        return userDao.getByUsername(username) != null;
    }
    /**
     Sends a request to the DOA with a userid and account name to check if the account exists in the database.
     **/
    private boolean accountExists(int userid, String accountName){
        return accountDao.getByAccountName(userid, accountName) != null;
    }
    /**
     Prompts the user for their username and password and collects their input.
     **/
    private String[] usernameAndPasswordInput(){
        menuService.enterUsernamePrompt();
        String username = scanner.nextLine();
        menuService.enterPasswordPrompt();
        String password = scanner.nextLine();
        return new String[]{username, password};
    }
    /**
     Prompts the user to choose between a checkings and savings account.
     **/
    private AccountType getAccountType(){
        String type;
        while(true) {
            menuService.enterAccountType();
            type = scanner.nextLine();
            switch (type) {
                case "1":
                    return AccountType.Checkings;
                case "2":
                    return AccountType.Savings;
                default:
                    menuService.incorrectMenuSelection();
            }
        }
    }
    /**
     Prompts the user to give a non-negative double value.
     **/
    private double getDouble() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                double number = scanner.nextDouble();
                if (number >= 0) {
                    return number;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }
    /**
     Collects an account name and type from the user and passes it to the
     DAO to create an account in the database.
     **/
    public void createBankAccount(){
        menuService.enterAccountName();
        String accountName = scanner.nextLine();
        AccountType accountType = getAccountType();

        if(!accountExists(currentUser.getUserId(), accountName)){
            accountDao.create(new BankAccount(currentUser.getUserId(), accountName, 0, accountType));
            menuService.accountCreated();
        } else{
            menuService.accountAlreadyExists();
        }
    }
    /**
     Collects an account name and passes it to the DAO to delete the
     account from the database.
     **/
    public void deleteBankAccount(){
        menuService.enterAccountNameDelete();
        String accountName = scanner.nextLine();

        if(accountExists(currentUser.getUserId(), accountName)){
            accountDao.deleteById(currentUser.getUserId(), accountName);
            menuService.accountDeleted();
        } else{
            menuService.accountDoesNotExist();
        }
    }
    /**
     Collects an account name and number and attempts to withdraw the
     specified number of funds from an account in the database. If there
     are not enough funds as requested, the transaction will not complete.
     **/
    public void withdrawFunds() {
        menuService.enterAccountName();
        String accountName = scanner.nextLine();
        menuService.withdraw();
        Double withdraw = getDouble();
        BankAccount currentAccount = accountDao.getByAccountName(currentUser.getUserId(), accountName);

        if (currentAccount != null) {
            if (currentAccount.getBalance() >= withdraw){
                currentAccount.setBalance(currentAccount.getBalance() - withdraw);
                accountDao.update(currentAccount);
                accountDao.transaction(currentAccount.getAccountId(), currentAccount.getAccountName(),withdraw * -1.0);
                menuService.transactionComplete();
            }else{
                menuService.transactionError();
            }
        } else {
            menuService.accountDoesNotExist();
        }
    }
    /**
     Collects an account name and number and deposits the
     specified number of funds into the account in the database.
     **/
    public void depositFunds() {
        menuService.enterAccountName();
        String accountName = scanner.nextLine();
        menuService.deposit();
        Double deposit = getDouble();
        BankAccount currentAccount = accountDao.getByAccountName(currentUser.getUserId(), accountName);

        if (currentAccount != null) {
            currentAccount.setBalance(currentAccount.getBalance() + deposit);
            accountDao.update(currentAccount);
            accountDao.transaction(currentAccount.getAccountId(), currentAccount.getAccountName(), deposit);
            menuService.transactionComplete();
        } else {
            menuService.accountDoesNotExist();
        }
    }
    /**
     Collects an account name and sends a request to view the current
     balance of an account.
     **/
    public void viewBalance() {
        menuService.enterAccountName();
        String accountName = scanner.nextLine();
        BankAccount currentAccount = accountDao.getByAccountName(currentUser.getUserId(), accountName);

        if (currentAccount != null) {
            menuService.viewBalance(currentAccount.getBalance());
        } else {
            menuService.accountDoesNotExist();
        }
    }
    /**
     Collects an account name and then requests all the transaction history
     for that account from the database.
     **/
    public void viewTransactionHistory(){
        menuService.enterAccountName();
        String accountName = scanner.nextLine();
        BankAccount currentAccount = accountDao.getByAccountName(currentUser.getUserId(), accountName);

        if (currentAccount != null) {
            accountDao.getAllTransactions(currentAccount.getAccountId());
        } else {
            menuService.accountDoesNotExist();
        }
    }
}