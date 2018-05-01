package home.logic;

import java.util.logging.Logger;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.servlet.HttpSession;

public class AccessControl {

    private static final Logger LOGGER = Logger.getLogger(AccessControl.class.getName());
    public static final String ATTR_LOGGEDIN = "loggedin";

    private ServerConfig serverConfig;
    private HttpSession session;

    public AccessControl(ServerConfig serverConfig, HttpSession session) {
        this.serverConfig = serverConfig;
        this.session = session;
    }

    public boolean isLogged() {
        if (session == null) {
            LOGGER.fine("No session, not logged in");
            return false;
        }

        if (session.getAttribute(ATTR_LOGGEDIN) != null) {
            if (session.getAttribute(ATTR_LOGGEDIN).equals("1")) {
                return true;
            } else {
                LOGGER.fine("Not logging in - session attribute is NOT null");
            }
        } else {
            LOGGER.fine("Not logging in - session attribute is null");
        }

        return false;
    }
    public void logout() {
        session.setAttribute(ATTR_LOGGEDIN, null);
    }

    public boolean doLogin(String login, String password) {
        if (serverConfig.getAttribute("admin.login").equals(login)
                && serverConfig.getAttribute("admin.password").equals(password)) {
            session.setAttribute(ATTR_LOGGEDIN, "1");
            return true;
        } else {
            LOGGER.fine("Not logging in - wrong password");
        }
        return false;
    }
}
