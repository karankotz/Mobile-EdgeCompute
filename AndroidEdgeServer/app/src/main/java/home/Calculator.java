package home;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.utilities.Utilities;

import java.util.logging.Logger;

import home.logic.AccessControl;
import home.logic.HTMLDocument;

public class Calculator extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
  public static final String RELOCATE_PARAM_NAME = "relocate";
  String resultstring="";
  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    ServerConfig serverConfig = (ServerConfig) getServletContext().getAttribute(ServerConfig.class.getName());

    HTMLDocument doc = new HTMLDocument("Calculator", false);
    doc.setOwnerClass(getClass().getSimpleName());

    doc.writeln("<div class=\"Calculator\">");

    doc.writeln("<h2>Basic Simple Calculator</h2>");

    if (request.getMethod().equals(HttpRequestWrapper.METHOD_POST)) {

      LOGGER.fine("Using Calculator Application");

      String a1 = request.getPostParameter("num1");
      String a2 = request.getPostParameter("num2");
      String op = request.getPostParameter("op");
      if (request.getPostParameter("op") != null) {

        if (op.equals("Addition")) {

          Double result = Double.parseDouble(a1) + Double.parseDouble(a2);
          resultstring = Double.toString(result);

          //request.getRequestDispatcher("/hello.jsp").forward(request, response);
        } else if (op.equals("Subtraction")) {
          Double result2 = Double.parseDouble(a1) - Double.parseDouble(a2);
          resultstring = Double.toString(result2);

        } else if (op.equals("multiplication")) {
          Double resultmult = Double.parseDouble(a1) * Double.parseDouble(a2);
          resultstring = Double.toString(resultmult);

        } else {
          //out.println(Integer.parseInt(n1) / Integer.parseInt(n2));
          Double resultmult = Double.parseDouble(a1) / Double.parseDouble(a2);
          resultstring = Double.toString(resultmult);
        }
      }
    }

    String location = "/home/Calculator";
    if (request.getParameter(RELOCATE_PARAM_NAME) != null) {
      location += "?" + RELOCATE_PARAM_NAME + "=" + Utilities.urlEncode(request.getParameter(RELOCATE_PARAM_NAME));
    }

    String form = "<form action=\""
      + location
      + "\" method=\"post\">\n"
      + "    Enter Number 1:\n" +
      "    <input name=\"num1\"  type=\"text\" class=\"form-control\" placeholder=\"Number 1\" required autofocus />\n" +
      "    Enter Number 2:\n" +
      "    <input name=\"num2\" type=\"text\"class=\"form-control\" placeholder=\"Number 2\" required autofocus />\n" +
      "    Operation:\n" +
      "    <select name=\"op\">\n" +
      "        <option value=\"Addition\" >Addition</option>\n" +
      "        <option value=\"Subtraction\">Subtraction</option>\n" +
      "        <option value=\"multiplication\">multiplication</option>\n" +
      "        <option value=\"division\">division</option>\n" +
      "    </select>\n" +
      "    <input class=\"btn btn-lg btn-primary btn-block\" type=\"submit\" value=\"Calculate\" />\n" +
      "</form>";
    doc.write(form);
    doc.writeln("</div>");
    if(resultstring != "")
    {
      doc.writeln("<h4>Result:</h4>");
      doc.writeln(resultstring);
    }
    response.getWriter().print(doc.toString());
  }




}
