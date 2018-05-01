package example;

import java.io.PrintWriter;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

import static com.edge.http.Headers.HEADER_TRANSFER_ENCODING;


public class ChunkedWithDelay extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.getHeaders().setHeader(HEADER_TRANSFER_ENCODING, "chunked");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<table style='height: 40px; width: 100%; border: 0; cellspacing: 0;'>");
        printWriter.println("<tr><td style='background-color: green'></td>");
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
            }
            printWriter.println("<td style='background-color: black'></td>");
            printWriter.flush();
        }
        printWriter.println("<tr></table>");
    }
}
