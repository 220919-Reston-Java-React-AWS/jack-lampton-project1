package com.revature.repository;
import com.revature.model.User;
import java.sql.*;

public class UserRepository {

    //register a new user
    public User addUser(User user) throws SQLException  {

        try(Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "insert into users (first_name, last_name, username, userpassword, role_id) values (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, 1);

            int numberOfRecordsAdded = pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int id = rs.getInt(1);

            return new User(id, user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), 1);
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            String sql = "SELECT * FROM users WHERE users.username = ? AND users.userpassword = ?";
            PreparedStatement pstmt = connectionObject.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
        //if username and password check out from SQL, a new user object is created here in Java
            if (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                String un = rs.getString("username");
                String pw = rs.getString("userpassword");
                int roleId = rs.getInt("role_id");

                return new User(id, firstname,lastname, un, pw, roleId);
            } else {
                return null;
            }
        }
    }
}
