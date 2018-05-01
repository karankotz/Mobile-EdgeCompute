package example;

import java.io.PrintWriter;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

import static com.edge.http.Headers.HEADER_TRANSFER_ENCODING;


public class Chunked extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        response.getHeaders().setHeader(HEADER_TRANSFER_ENCODING, "chunked");
        PrintWriter printWriter = response.getWriter();
        printWriter.print("This is an example of chunked transfer type. ");
        printWriter.flush();
        printWriter.print("Chunked transfer type can be used when the final length of the data is not known.");
    }
}
