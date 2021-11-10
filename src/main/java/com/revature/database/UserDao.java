package com.revature.database;

import com.revature.user.User;
import com.revature.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 Database access object for users. Connects to the postgresql database
 and sends sql queries/requests using input from the UserService.
 **/
public class UserDao implements DaoInterface<User>{
    /**
     Sends a request to the database to create a user.
     **/
    public void create(User user) {
        String sql = "insert into users(username, password) values(?,?)";
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     Sends a request to the database to get a user's information based on their userid.
     **/
    public User getById(int userid) {
        String sql = "select * from users where user_id=?";
        User user = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userid);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return user;
    }
    /**
     Sends a request to the database to get a user's information based on their username.
     **/
    public User getByUsername(String username){
        String sql = "select * from users where username=?";
        User user = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return user;
    }
    /**
     Sends a request to the database to get all users in the database and
     prints their information out to the console.
     **/
    public void getAll() {
        String sql = "select * from users";
        User user = null;
        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                user = new User();
                user.setUserId(rs.getInt(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                System.out.println(user);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     Sends a request to the database with the new balance of an account after a transaction.
     **/
    public boolean update(User user) {
        String sql = "update users set username=?, password=? where user_id=?";

        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getUserId());
            return stmt.executeUpdate() != 0;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    /**
     Sends a request to the database to delete a user.
     **/
    public boolean deleteById(int id) {
        String sql = "delete from users where user_id=?";

        try(Connection connection = ConnectionService.getConnection()){
            assert connection != null;
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() != 0;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}