package by.epamlab.controllers;

import by.epamlab.bean.User;
import by.epamlab.exceptions.DAOException;
import by.epamlab.exceptions.ValidationException;
import by.epamlab.interfaces.IUserManager;
import by.epamlab.resources.Constants;
import by.epamlab.validators.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    private IUserManager userManager;

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String authorization(HttpSession session, HttpServletRequest request) {
        String login = (String) request.getAttribute(Constants.LOGIN);
        String password = (String) request.getAttribute(Constants.PASSWORD);
        try {
            UserValidator.validate(login, password);
            User user = userManager.getUser(login, password);
            session.setAttribute(Constants.USER, user);
            return "redirect:/tasks/today";
        } catch (DAOException | ValidationException e) {
            request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return "forward:/authorization.jsp";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(HttpSession session, HttpServletRequest request) {
        String login = (String) request.getAttribute(Constants.LOGIN);
        String password = (String) request.getAttribute(Constants.PASSWORD);
        String email = (String) request.getAttribute(Constants.EMAIL);
        try {
            UserValidator.validate(login, password, email);
            User user = userManager.createUser(login, password, email);
            session.setAttribute(Constants.USER, user);
            return "redirect:/tasks/today";
        } catch (DAOException | ValidationException e) {
            request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return "forward:/registration.jsp";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String Logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
