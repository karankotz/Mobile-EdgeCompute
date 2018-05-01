package example;

import java.io.IOException;
import java.nio.charset.Charset;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.HttpServlet;

public class Streaming extends HttpServlet {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            byte[] message = "<p>Writing to output stream directly, without chunking.</p>".getBytes(CHARSET);
            response.setContentLength(message.length);
            response.getOutputStream().write(message);
        } catch (IOException e) {
            throw new ServletException("Unable to write to output stream", e);
        }
    }
}
