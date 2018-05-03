package login;
import Util.ConstantUtil;

import java.sql.*;

/**
 * validation for user credentials
 */
public class Validate
{
    /**
     * values given password and username
     * @param username username for the user
     * @param password password for the user
     * @return validation result
     * @throws SQLException
     */
    public static boolean checkUser(String username,String password) throws SQLException {
        return (username.equals(ConstantUtil.USERNAME) && password.equals(ConstantUtil.PASSWORD));
    }
}