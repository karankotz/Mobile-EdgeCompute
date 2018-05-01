package home.filter;

import java.io.IOException;

import home.logic.AccessControl;
import com.edge.http.configuration.ServerConfig;
import com.edge.http.exception.ServletException;
import com.edge.http.servlet.Filter;
import com.edge.http.servlet.FilterChain;
import com.edge.http.servlet.FilterConfig;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

import static home.Login.RELOCATE_PARAM_NAME;

public class SecurityFilter implements Filter {

    private FilterConfig filterConfig;
    private ServerConfig serverConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        serverConfig = (ServerConfig) filterConfig.getServletContext().getAttribute(ServerConfig.class.getName());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        AccessControl ac = new AccessControl(serverConfig, request.getSession());
        if (!ac.isLogged()) {
            response.sendRedirect(filterConfig.getServletContext().getContextPath() + "/Login?" + RELOCATE_PARAM_NAME + "=" + request.getRequestURI() + (!request.getQueryString().equals("") ? "?" + request.getQueryString() : ""));
            return;
        }

        filterChain.doFilter(request, response);
    }
}
