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
        //resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
    /*    out.println("<html>");
        out.println("<head>");
        out.println("<title>Hola</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");
        out.println("<h1>Result :: "+result+"<h1>");
        out.println("<a href=\"convert.jsp\">Back<a>");
        out.println("</body>");
        out.println("</html>");*/
        /**out.println("<script type=\"text/javascript\">");
        out.println("alert('Result: "+result+"');");
        out.println("location='nqueen.jsp';");
        out.println("</script>");**/
        out.println(result);
    }
}
