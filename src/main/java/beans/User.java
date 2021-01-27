package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@Named()
@RequestScoped()
@ManagedBean

public class User {

    private String login;
    private String email;
    private String passwd;
    private String cookies_number;

    public void setLogin(String login) { this.login = login; }
    public String getLogin() {
        return login;
    }

    public void setEmail(String email) { this.email = email; }
    public String getEmail() {
        return email;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getPasswd() {
        return passwd;
    }

    public void setCookies_number(String cookies_number) {
        this.cookies_number = cookies_number;
    }
    public String getCookies_number() {
        return cookies_number;
    }

    public String getUserLogin() throws SQLException {
        Database database = new Database();
        Cookies cookies = new Cookies();
        return database.getUserLogin(cookies.getCookieUserEmail());
    }

    public String register() throws SQLException {
        Database database = new Database();
        Connection connection = null;
        Random random = new Random();
        String random_number = String.valueOf(random.nextInt(9999999) + 1);
        this.setCookies_number(random_number);
        connection = database.connect();
        if(connection != null){
            //PreparedStatement stmt = connection.prepareStatement("insert into users(login,email,passwd,cookies_number) values(?,?,?,?);")
            try (PreparedStatement stmt = connection.prepareStatement("insert into users(login, email, passwd, cookies_number) values (?,?,?,?);")) {
                stmt.setString(1, this.getLogin());
                stmt.setString(2, this.getEmail());
                Password passwordHash = new Password();
                stmt.setString(3, passwordHash.hash256(this.getPasswd()));
                stmt.setString(4, this.getCookies_number());
                stmt.executeUpdate();
            } catch (Exception e) {
            }
            connection.close();
        }
        return "submit";
    }

    public String login() throws SQLException {
        boolean redirect = false;
        String statement = "SELECT * FROM users WHERE email = ? and passwd = ?;";
        Cookies cookies = new Cookies();
        Password passwordHash = new Password();
        Database database = new Database();
        Connection connection = database.connect(); /*** this database ***/
        if(connection != null) {
            try (PreparedStatement stmt = connection.prepareStatement(statement)) {
                stmt.setString(1, this.getEmail());
                stmt.setString(2, passwordHash.hash256(this.getPasswd()));
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    redirect = true;
                    cookies.setCookie(this.getEmail(), database.getUserNumber(this.getEmail()));
                }
            }
        }
        if(connection != null) connection.close();
        return redirect(redirect); /**  return redirect(redirect); <--- return type change to String **/
    }

    public String redirect(boolean redirect) {
        String redirect_string = "failed";
        if (redirect) {
            redirect_string = "login";
        }
        return redirect_string;
    }

    public boolean isUserLogged(){
        boolean logged = false;
        Cookies cookies = new Cookies();
        try{
            if(!cookies.getCookieUserNumber().equals("")){
                logged = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logged;
    }
}
