package example;

import java.util.Date;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.Cookie;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.HttpServlet;


public class Cookies extends HttpServlet {

    private final static String PAGE_HITS_COOKIE_NAME = "page_hits";
    private final static String FIRST_VISITED_AT_COOKIE_NAME = "first_visited_at";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        int pageHits = 0;
        if (request.getCookie(PAGE_HITS_COOKIE_NAME) != null) {
            pageHits = Integer.parseInt(request.getCookie(PAGE_HITS_COOKIE_NAME).getValue());
        }
        ++pageHits;
        response.addCookie(new Cookie(PAGE_HITS_COOKIE_NAME, pageHits));

        String firstVisitedAt;
        if (request.getCookie(FIRST_VISITED_AT_COOKIE_NAME) != null) {
            firstVisitedAt = request.getCookie(FIRST_VISITED_AT_COOKIE_NAME).getValue();
        } else {
            firstVisitedAt = (new Date()).toString();
            response.addCookie(new Cookie(FIRST_VISITED_AT_COOKIE_NAME, firstVisitedAt));
        }

        response.getWriter().println("<p>Cookie page hits: " + pageHits + "</p>");
        response.getWriter().println("<p>First visited at: " + firstVisitedAt + "</p>");
    }
}
