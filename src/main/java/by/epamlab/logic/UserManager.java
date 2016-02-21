package by.epamlab.logic;

import by.epamlab.bean.User;
import by.epamlab.exceptions.FileSystemException;
import by.epamlab.interfaces.IUserDAO;
import by.epamlab.interfaces.IUserManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import by.epamlab.resources.Constants;

import java.io.IOException;


public class UserManager implements IUserManager {

    public static final Logger LOG = Logger.getLogger(UserManager.class);

    private IUserDAO userDao;

    public UserManager(IUserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User createUser( String login, String password, String email) {
        try {
            User user = userDao.addUser(login, email, password);
            TaskFile.createFileFolder(user);
            return user;
        }catch (IOException e) {
            LOG.error(e);
            throw new FileSystemException(Constants.ERROR_CREATE_FILE_DIRECTORY);
        }
    }

    @Override
    public User getUser(String login, String password) {
        return userDao.getUser(login,password);
    }
}
