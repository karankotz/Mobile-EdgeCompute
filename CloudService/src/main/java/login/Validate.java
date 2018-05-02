package login;
import Util.ConstantUtil;

import java.sql.*;


public class Validate
{
    public static boolean checkUser(String username,String password) throws SQLException {
        return (username.equals(ConstantUtil.USERNAME) && password.equals(ConstantUtil.PASSWORD));
    }
}