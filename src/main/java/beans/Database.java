package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Named()
@RequestScoped()
@ManagedBean

public class Database {

    static final String DB_USER = System.getProperty("db.usr");
    static final String DB_PASSWORD = System.getProperty("db.passwd");

    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/bugfinder_dev?", DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException exception) {
        }
        return connection;
    }

}
