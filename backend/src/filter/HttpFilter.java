package filter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class HttpFilter implements Filter
{
    public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException;

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
    }

    @Override
    public void destroy() {}
}