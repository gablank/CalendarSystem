package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.User;

import java.sql.*;
import java.util.Properties;


public class Database {
    private Connection connection;

    public Database() {
        //Class.forName("com.mysql.jdbc.Driver");
        Properties login_credentials = new Properties();
        login_credentials.put("user", "cs_user");
        login_credentials.put("password", "cs_password");

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/", login_credentials);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns id of the new user
    public int insert_user(User user) {
        String query = "INSERT INTO users ";
        query += "(id, username, password, first_name, last_name, email) ";
        query += "VALUES ('', ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user.get_username());
            preparedStatement.setString(2, user.get_password());
            preparedStatement.setString(3, user.get_firstname());
            preparedStatement.setString(4, user.get_lastname());
            preparedStatement.setString(5, user.get_email());
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet query(String query) {
        ResultSet result = null;
        Statement statement = null;

        try {
            statement = this.connection.createStatement();

            result = statement.executeQuery(query);


        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
