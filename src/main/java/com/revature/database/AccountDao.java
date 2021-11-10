package com.revature.database;

import com.revature.bankAccount.BankAccount;
import com.revature.bankAccount.AccountType;
import com.revature.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 Database access object for bank accounts. Connects to the postgresql database
 and sends sql queries/requests using input from the UserService.
 **/
public class AccountDao implements DaoInterface<BankAccount>{
    /**
     Sends a request to the database to create a bank account.
     **/
    public void create(BankAccount bankAccount) {
        String sql = "insert into accounts(user_id, account_name, balance, account_type) values(?,?,?,?)";
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, bankAccount.getUserId());
            stmt.setString(2, bankAccount.getAccountName());
            stmt.setDouble(3, bankAccount.getBalance());
            stmt.setInt(4, bankAccount.getAccountType().ordinal()+1);

            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     Sends a request to the database to get an account's information based on its accountid.
     **/
    public BankAccount getById(int accountid) {
        String sql = "select * from users where account_id=?";
        BankAccount bankAccount = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountid);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                bankAccount = new BankAccount();
                bankAccount.setAccountId(rs.getInt(1));
                bankAccount.setUserId(rs.getInt(2));
                bankAccount.setAccountName(rs.getString(3));
                bankAccount.setBalance(rs.getDouble(4));
                bankAccount.setAccountType(AccountType.values()[rs.getInt(5)-1]);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }
    /**
     Sends a request to the database to get an account's information based on its userid and account name.
     **/
    public BankAccount getByAccountName(int userid, String accountName){
        String sql = "select * from accounts where user_id=? and account_name=?";
        BankAccount bankAccount = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userid);
            stmt.setString(2, accountName);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                bankAccount = new BankAccount();
                bankAccount.setAccountId(rs.getInt(1));
                bankAccount.setUserId(rs.getInt(2));
                bankAccount.setAccountName(rs.getString(3));
                bankAccount.setBalance(rs.getDouble(4));
                bankAccount.setAccountType(AccountType.values()[rs.getInt(5)-1]);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return bankAccount;
    }

    /**
     Sends a request to the database to get all accounts in the database and
     prints their information out to the console.
     **/
    public void getAll() {
        String sql = "select * from accounts";
        BankAccount bankAccount = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                bankAccount = new BankAccount();
                bankAccount.setAccountId(rs.getInt(1));
                bankAccount.setUserId(rs.getInt(2));
                bankAccount.setAccountName(rs.getString(3));
                bankAccount.setBalance(rs.getDouble(4));
                bankAccount.setAccountType(AccountType.values()[rs.getInt(5)-1]);
                System.out.println(bankAccount);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     Sends a request to the database with the new balance of an account after a transaction.
     **/
    public boolean update(BankAccount bankAccount) {
        String sql = "update accounts set balance=? where user_id=? and account_name=?";
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, bankAccount.getBalance());
            stmt.setInt(2, bankAccount.getUserId());
            stmt.setString(3, bankAccount.getAccountName());
            return stmt.executeUpdate() != 0;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Sends a request to the database to delete an account.
     **/
    public boolean deleteById(int userid, String accountName) {
        String sql = "delete from accounts where user_id=? and account_name=?";
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userid);
            stmt.setString(2, accountName);
            return stmt.executeUpdate() != 0;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Sends a request to the database to log a transaction.
     **/
    public boolean transaction(int accountid, String accountName, double change){
        String sql = "insert into transactions(account_id,account_name, change) values(?,?,?)";
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountid);
            stmt.setString(2, accountName);
            stmt.setDouble(3, change);
            return stmt.executeUpdate() != 0;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Sends a request to the database for all the transaction history of an account.
     **/
    public void getAllTransactions(int accountid) {
        String sql = "select * from transactions where account_id=?";
        BankAccount bankAccount = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountid);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                System.out.println(
                        "TransactionId=" + rs.getInt(1) +
                        ", change=" + rs.getDouble(4));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}