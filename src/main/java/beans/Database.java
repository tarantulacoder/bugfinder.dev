package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.*;

@Named()
@RequestScoped()
@ManagedBean

public class Database {

    static final String DB_USER = System.getProperty("db.usr");
    static final String DB_PASSWORD = System.getProperty("db.passwd");

    private final String statement = "SELECT * FROM users WHERE email = ?;";

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/bugfinder_dev?", DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException exception) {
        }
        return connection;
    }



    public String getFullName(String email) throws SQLException {
        String fullName = "";
        Connection connection = this.connect();
        if(connection != null){
            try (PreparedStatement stmt = connection.prepareStatement(statement)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    fullName = rs.getString("full_name");
                }
            }
        }
        assert connection != null;
        connection.close();
        return fullName;
    }
    public String getUserLogin(String email) throws SQLException {
        String userLogin = "";
        Connection connection = this.connect();
        if(connection != null){
            try (PreparedStatement stmt = connection.prepareStatement(statement)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    userLogin = rs.getString("login");
                }
            }
        }
        if(connection != null) connection.close();
        return userLogin;
    }
    public String getUserNumber(String email) throws SQLException {
        String userNumber = "";
        Connection connection = this.connect();
        if(connection != null){
            try (PreparedStatement stmt = connection.prepareStatement(statement)) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()){
                    userNumber = rs.getString("cookies_number");
                }
            }
        }
        if(connection != null) connection.close();
        return userNumber;
    }
}
