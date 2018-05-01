package example.filter;

import java.io.IOException;

import com.edge.http.exception.AccessDeniedException;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.Filter;
import com.edge.http.servlet.FilterChain;
import com.edge.http.servlet.FilterConfig;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

public class FakeSecuredFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        throw new AccessDeniedException();
    }
}
