package by.epamlab.interfaces;

import by.epamlab.bean.User;


public interface IUserDAO {
    User getUser(String login, String password);

    User addUser(String login, String email, String password);

}
