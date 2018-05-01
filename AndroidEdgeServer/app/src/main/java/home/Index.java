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
        return doc;
    }
}
