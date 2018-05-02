package login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import Util.*;

public class Register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        System.out.println("UserInfo:: "+username+" "+email+" "+pass);
        try{
            //loading drivers for mysql
            Class.forName("com.mysql.jdbc.Driver");
            //creating connection with the database
            try( Connection con = DriverManager.getConnection
                    ("jdbc:mysql://"+ ConstantUtil.HOST+":"+ConstantUtil.PORT+"/"+ConstantUtil.DB_NAME, ConstantUtil.USERNAME, ConstantUtil.PASSWORD);
                 PreparedStatement ps = con.prepareStatement
                         ("insert into users values(?,?,?)")) {

                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, pass);
                int i = ps.executeUpdate();
                if(i>0)
                {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Successfully registered');");
                    out.println("location='index.jsp';");
                    out.println("</script>");
                }
                else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Successfully registered');");
                    out.println("location='register.jsp';");
                    out.println("</script>");
                }
            }
        }
        catch(Exception se)
        {
            se.printStackTrace();
        }

    }
}