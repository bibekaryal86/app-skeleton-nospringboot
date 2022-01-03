package nospring.service.skeleton.app.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import nospring.service.skeleton.app.util.Util;
import java.util.List;

@Slf4j
public class ServletFilter implements Filter {

    private static final List<String> DO_NOT_FILTER = List.of(
            Util.CONTEXT_PATH + "/tests/ping",
            Util.CONTEXT_PATH + "/tests/reset");

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        logRequest(httpServletRequest);

        if (DO_NOT_FILTER.contains(httpServletRequest.getRequestURI()) ||
                Util.isAuthenticatedRequest(httpServletRequest)) {
            chain.doFilter(request, response);
            logResponse(httpServletRequest, httpServletResponse);
        } else {
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(401);
            httpServletResponse.getWriter().print("{\"errMsg\": \"Error! Authorization Missing!! Please Try Again!!!\"}");
            logResponse(httpServletRequest, httpServletResponse);
        }
    }

    private void logRequest(HttpServletRequest httpServletRequest) {
        log.info("REQUEST BEGIN: [ {} ] | [ {} ]", httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr());
    }

    private void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("REQUEST END: [ {} ] | [ {} ]", httpServletRequest.getRequestURI(), httpServletResponse.getStatus());
    }
}
