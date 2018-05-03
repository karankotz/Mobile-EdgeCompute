package login;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * login servlet
 */
public class Login extends HttpServlet {

    /**
     * Servlet to handle login request
     * @param request request from client
     * @param response response from server
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("username");
        String pass = request.getParameter("password");

        try {
            if(Validate.checkUser(email, pass))
            {
                RequestDispatcher rs = request.getRequestDispatcher("intermediate.jsp");
                rs.forward(request, response);
            }
            else
            {
                out.println("Username or Password incorrect");
                RequestDispatcher rs = request.getRequestDispatcher("index.jsp");
                rs.include(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}