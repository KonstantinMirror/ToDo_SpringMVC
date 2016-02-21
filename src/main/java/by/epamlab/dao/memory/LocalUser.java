package by.epamlab.dao.memory;

import by.epamlab.bean.User;
import by.epamlab.exceptions.DAOException;
import by.epamlab.interfaces.IUserDAO;
import by.epamlab.resources.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LocalUser implements IUserDAO {

    private DAOUserBuffer buffer = new DAOUserBuffer();

    @Override
    public User getUser(String login, String password) {
        return buffer.getUser(login, password);
    }

    @Override
    public User addUser(String login, String email, String password) {
        buffer.addUser(login, password, email);
        return null;
    }

    private class DAOUserBuffer {

        final int INDEX_PASSWORD = 0;
        final int INDEX_EMAIL = 1;
        final int INDEX_ROLE = 2;
        final String DEFAULT_ROLE = Constants.Role.USER.toString();
        public Map<String, List<String>> usersBuffer = new ConcurrentHashMap<>();

        public User getUser(String login, String password) {
            if (!isContain(login, password)) {
                throw new DAOException(Constants.NO_SUCH_USER);
            }
            List<String> subData = usersBuffer.get(login);
            User user = new User();
            user.setLogin(login);
            user.setEmail(subData.get(INDEX_EMAIL));
            user.setRole(subData.get(INDEX_ROLE));
            return user;
        }

        public boolean isContain(String login, String password) {
            if (usersBuffer.containsKey(login)) {
                String userPassword = usersBuffer.get(login).get(INDEX_PASSWORD);
                if (userPassword.equals(password)) {
                    return true;
                }
            }
            return false;
        }

        public synchronized void addUser(String login, String password, String email) {
            if (!isContain(login, password)) {
                List<String> subData = new ArrayList<>(3);
                subData.add(password);
                subData.add(email);
                subData.add(DEFAULT_ROLE);
                usersBuffer.put(login, subData);
            } else throw new DAOException(Constants.USER_ALREADY_EXISTS);
        }
    }
}
