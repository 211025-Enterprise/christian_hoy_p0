package com.revature;

import com.revature.service.MenuService;
import com.revature.service.UserService;

import java.util.Scanner;

/**
Main driver class to run program.
 **/
public class Driver {
    UserService userService;
    MenuService menuService;
    Scanner scanner;

    public Driver(){
        this.menuService = new MenuService();
        this.scanner = new Scanner(System.in);
        this.userService = new UserService(this.scanner, this.menuService);
    }

    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.mainMenu();
    }
    /**
     Loads the main menu for an unauthenticated user. Prints the menu through the menuService
     accepts input through the input service.
     **/
    public void mainMenu(){
        menuService.mainMenuPrompt();
        while(true) {
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    userService.createUser();
                    menuService.mainMenuPrompt();
                    break;
                case "2": {
                    if(userService.login()){
                        menuService.userMenuPrompt(userService.getCurrentUser());
                        userMenu();
                    }else{
                        menuService.invalidCredentials();
                    }
                    break;
                }
                case "0":
                    System.exit(0);
                    break;
                default:
                    menuService.incorrectMenuSelection();
                    menuService.mainMenuOptions();
            }
        }
    }
    /**
     Loads the main menu for an authenticated user. Prints the menu through the menuService
     accepts input through the input service.
     **/
    private void userMenu(){
        boolean isRunning = true;
        menuService.userMenuOptions();
        while(isRunning) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userService.createBankAccount();
                    menuService.userMenuOptions();
                    break;
                case "2":
                    userService.deleteBankAccount();
                    menuService.userMenuOptions();
                    break;
                case "3":
                    userService.withdrawFunds();
                    menuService.userMenuOptions();
                    break;
                case "4":
                    userService.depositFunds();
                    menuService.userMenuOptions();
                    break;
                case "5":
                    userService.viewBalance();
                    menuService.userMenuOptions();
                    break;
                case "6":
                    userService.viewTransactionHistory();
                    menuService.userMenuOptions();
                    break;
                case "0":
                    userService.logout();
                    menuService.mainMenuPrompt();
                    isRunning = false;
                    break;
                default:
                    menuService.incorrectMenuSelection();
                    menuService.userMenuOptions();
            }
        }
    }
}