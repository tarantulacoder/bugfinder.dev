package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Named()
@RequestScoped()
@ManagedBean

public class Post {

    private String userId;
    private String category;
    private String name;
    private String about;
    private String code;

    public void setUserId(String userId) { this.userId = userId; }
    public String getUserId() {
        return userId;
    }

    public void setCategory(String category) { this.category = category; }
    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    public String getAbout() {
        return about;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public String addCode() throws SQLException {
        Cookies cookies = new Cookies();
        this.setUserId(cookies.getCookieUserNumber());
        Database database = new Database();
        Connection connection = null;
        connection = database.connect();
        if(connection != null){
            try (PreparedStatement stmt = connection.prepareStatement("insert into codes(user_id,category,name,about,code) values(?,?,?,?,?);")) {
                stmt.setString(1, this.getUserId());
                stmt.setString(2, this.getCategory());
                stmt.setString(3, this.getName());
                stmt.setString(4, this.getAbout());
                stmt.setString(5, this.getCode());
                stmt.executeUpdate();
            }
            connection.close();
        }
        return "submit";
    }
}
