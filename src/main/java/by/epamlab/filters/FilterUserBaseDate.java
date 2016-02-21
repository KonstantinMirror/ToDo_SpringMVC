package by.epamlab.filters;

import by.epamlab.resources.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter({"/user/registration", "/user/authorization"})
public class FilterUserBaseDate extends AbstractFilter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        initAttribute(req, resp, chain, Constants.LOGIN, Constants.PASSWORD);
    }
}
