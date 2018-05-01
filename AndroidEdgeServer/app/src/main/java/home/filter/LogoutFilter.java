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

public class LogoutFilter implements Filter {

    private FilterConfig filterConfig;
    private ServerConfig serverConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        serverConfig = (ServerConfig) filterConfig.getServletContext().getAttribute(ServerConfig.class.getName());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        new AccessControl(serverConfig, request.getSession()).logout();
        response.sendRedirect(filterConfig.getServletContext().getContextPath() + "/Login");
    }
}
