package example;

import java.io.PrintWriter;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;


public class ForbiddenByFilter extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        PrintWriter printWriter = response.getWriter();
        printWriter.println("Hello World!");
    }
}
