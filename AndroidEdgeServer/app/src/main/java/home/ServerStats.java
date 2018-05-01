package home;
import home.logic.HTMLDocument;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
public class ServerStats extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        HTMLDocument doc = renderDocument();

        response.getWriter().print(doc.toString());
    }
    private HTMLDocument renderDocument() {
        HTMLDocument doc = new HTMLDocument("Basic Statistics");
        doc.setOwnerClass(getClass().getSimpleName());

        doc.writeln("<div class=\"page-header\"><h1>Basic Server statistics Displayed</h1></div>");
        doc.writeln("<table class=\"table\">");
        doc.writeln("<tr>");
        doc.writeln("   <td>Data received</td><td>" + com.edge.http.utilities.Utilities.fileSizeUnits(com.edge.http.Statistics.getBytesReceived()) + "</td>");
        doc.writeln("</tr>");
        doc.writeln("<tr>");
        doc.writeln("   <td>Data sent</td><td>" + com.edge.http.utilities.Utilities.fileSizeUnits(com.edge.http.Statistics.getBytesSent()) + "</td>");
        doc.writeln("</tr>");
        doc.writeln("<tr>");
        doc.writeln("   <td>Requests handled</td><td>" + com.edge.http.Statistics.getRequestsHandled() + "</td>");
        doc.writeln("</tr>");
        doc.writeln("<tr>");
        doc.writeln("   <td>404 errors</td><td>" + com.edge.http.Statistics.getError404s() + "</td>");
        doc.writeln("</tr>");
        doc.writeln("<tr>");
        doc.writeln("   <td>500 errors</td><td>" + com.edge.http.Statistics.getError500s() + "</td>");
        doc.writeln("</tr>");
        doc.writeln("</table");

        return doc;
    }
}
