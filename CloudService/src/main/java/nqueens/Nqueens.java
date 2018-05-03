package nqueens;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Nqueens extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int input = Integer.parseInt(req.getParameter("input"));


        N_Queen_helper np  = new N_Queen_helper();
        int result = np.main(input);
        PrintWriter out = resp.getWriter();
        out.println(result);
    }
}
