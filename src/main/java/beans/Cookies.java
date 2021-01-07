package beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.Map;

public class Cookies {
    public void setCookie(String email, String passwd) {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie("user_email", email, null);
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .addResponseCookie("user_number", passwd, null);
    }

    public String getCookieUserEmail() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        Cookie email = (Cookie) requestCookieMap.get("user_email");
        return email.getValue();
    }

    public String getCookieUserNumber() {
        Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestCookieMap();
        Cookie number = (Cookie) requestCookieMap.get("user_number");
        return number.getValue();
    }
}
