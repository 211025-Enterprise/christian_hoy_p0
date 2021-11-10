package com.revature.service;
import com.revature.user.User;

/**
 * MenuService is our console interface. It prints out options and error messages
 for the user.
 **/
public class MenuService {

    public void mainMenuPrompt(){
        System.out.println("Welcome to RevBank!");
        mainMenuOptions();
    }

    public void mainMenuOptions(){
        System.out.println("_____________________________");
        System.out.println("1) Create an Account");
        System.out.println("2) Login to your Account");
        System.out.println("0) Exit the Application");
        System.out.println("_____________________________");
    }

    public void userMenuOptions(){
        System.out.println("_____________________________");
        System.out.println("1) Create an Account");
        System.out.println("2) Delete an Account");
        System.out.println("3) Withdraw Funds");
        System.out.println("4) Deposit Funds");
        System.out.println("5) View Current Balance");
        System.out.println("6) View Transaction History");
        System.out.println("0) Logout");
        System.out.println("_____________________________");
    }

    public void enterAccountType(){
        System.out.println("_____________________________");
        System.out.println("1) Create a Savings Account");
        System.out.println("2) Create a Checkings Account");
        System.out.println("_____________________________");
    }

    public void userMenuPrompt(User user){
        System.out.println("Welcome, " + user.getUsername());
    }

    public void incorrectMenuSelection(){
        System.out.println("Please enter a valid option.");
    }

    public void enterUsernamePrompt(){
        System.out.println("Please enter your username: ");
    }

    public void enterPasswordPrompt(){
        System.out.println("Please enter your password: ");
    }

    public void enterAccountName(){
        System.out.println("Please enter a name for your account: ");
    }

    public void enterAccountNameDelete(){
        System.out.println("Please enter the name of the account you want to delete: ");
    }

    public void usernameAlreadyExists(){
        System.out.println("Username already exists.");
    }

    public void accountCreated(){
        System.out.println("Account successfully created!");
    }

    public void accountDeleted(){
        System.out.println("Account successfully deleted!");
    }

    public void accountAlreadyExists(){
        System.out.println("Account already exists.");
    }

    public void accountDoesNotExist(){
        System.out.println("Account does not exist.");
    }

    public void invalidCredentials(){
        System.out.println("Your credentials were incorrect.");
    }

    public void withdraw(){
        System.out.println("Enter amount to withdraw");
    }

    public void deposit(){
        System.out.println("Enter amount to deposit");
    }

    public void transactionComplete(){
        System.out.println("Transaction successfull.");
    }

    public void transactionError(){
        System.out.println("Unable to complete transaction.");
    }

    public void viewBalance(double balance){
        System.out.println("Your balance is: $" + balance);
    }
}