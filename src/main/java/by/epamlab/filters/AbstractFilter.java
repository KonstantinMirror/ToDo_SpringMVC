package by.epamlab.filters;

import by.epamlab.resources.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public abstract class AbstractFilter implements Filter {
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean isEmptyOrNull(String... string) {
        boolean flag = false;
        for (String tmp : string) {
            if (tmp == null || tmp.trim().isEmpty()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private String getBackUrl(HttpServletRequest request) {
        String uri = request.getHeader("Referer");
        int index = uri.lastIndexOf("/");
        return uri.substring(index);
    }

    protected void initAttribute(ServletRequest req, ServletResponse resp, FilterChain chain, String... param) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String[] paramValue = new String[param.length];
        for (int i = 0; i < param.length; i++) {
            paramValue[i] = request.getParameter(param[i]);
        }
        if (!isEmptyOrNull(paramValue)) {
            for (int i = 0; i < param.length; i++) {
                request.setAttribute(param[i], paramValue[i]);
            }
            chain.doFilter(req, resp);
        } else {
            request.setAttribute(Constants.ERROR_MESSAGE, Constants.SOME_FIELDS_IS_EMPTY);
            request.getRequestDispatcher(getBackUrl(request)).forward(req, resp);
        }
    }


}
