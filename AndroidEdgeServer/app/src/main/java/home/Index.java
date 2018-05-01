package home;
import home.logic.HTMLDocument;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

public class Index extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HTMLDocument doc = renderDocument();
        response.getWriter().print(doc.toString());
    }

    private HTMLDocument renderDocument() {
        HTMLDocument doc = new HTMLDocument("About");
        doc.setOwnerClass(getClass().getSimpleName());

        doc.writeln("<div class=\"page-header\"><h1>About</h1></div>");
        doc.write("<p> Web Server running from the Edge Device</p>");
        doc.write("<form action=\"http://app-env-smaple.us-west-2.elasticbeanstalk.com\">\n" +
                "    <input type=\"submit\" value=\"Go to AWS Cloud\" />\n" +
                "</form>");

        //doc.write("<br /><button class=\"btn btn-lg btn-primary btn-block\" onClick=\"http://app-env-smaple.us-west-2.elasticbeanstalk.com/\">Cloud</button>\n");

        return doc;
    }
}
