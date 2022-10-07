package com.revature.service;

import com.revature.exception.DupeUsernameException;
import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import com.revature.repository.UserRepository;

import java.sql.SQLException;
//authservice exists to do logins
public class AuthService {

    private UserRepository uRepo = new UserRepository();
    private User user;

    public void register(User user) throws SQLException {

        uRepo.addUser(user);
    }

    public User login(String username, String password) throws SQLException, InvalidLoginException, DupeUsernameException {

        User user = uRepo.getUserByUsernameAndPassword(username,password);

        //unsuccessful login
        if (user == null) {
            throw new InvalidLoginException("Please enter a valid username and password");
        }



        //successful login returns a user
        return  user;
    }
}
