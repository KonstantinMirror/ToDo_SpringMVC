package by.epamlab.interfaces;


import by.epamlab.bean.User;


public interface IUserManager {

    User createUser(String login,String password,String email);
    User getUser(String login, String password);
}
