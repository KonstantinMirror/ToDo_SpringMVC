package by.epamlab.dao.db;

import by.epamlab.bean.User;
import by.epamlab.exceptions.DAOException;
import by.epamlab.interfaces.IUserDAO;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.log4j.Logger;
import by.epamlab.resources.Constants;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class DBUser implements IUserDAO {
    public static final Logger LOG = Logger.getLogger(DBUser.class);

    @Inject
    private JdbcTemplate jdbcTemplate;

    private static final String EMAIL = "email";
    private static final String ROLE = "role";
    private static final String ID_LOGIN = "idLogin";
    private final ReentrantLock lock = new ReentrantLock();


    @Override
    public User getUser(String login, String password) {
        String USER_QUERY = "SELECT  * " +
                "FROM users.logins " +
                "WHERE login = ? AND pwd = ? ";
        try {
            return jdbcTemplate.queryForObject(USER_QUERY, new Object[]{login,
                    password}, (resultSet, i) -> {
                User user = new User();
                user.setRole(resultSet.getString(ROLE));
                user.setEmail(resultSet.getString(EMAIL));
                user.setIdUser(resultSet.getLong(ID_LOGIN));
                user.setLogin(login);
                return user;
        });
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            throw new DAOException(Constants.NO_SUCH_USER, e);
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public User addUser(String login, String email, String password) {
        String ADD_QUERY = "INSERT INTO logins (login, pwd, email) \n" +
                "VALUES(?,?,?)";
        lock.lock();
        try {
            jdbcTemplate.update(ADD_QUERY, login,
                    password, email);
            Long id = DBUtility.getID(jdbcTemplate);
            return new User(login, email, id);
        } catch (DuplicateKeyException e) {
            LOG.info(e);
            throw new DAOException(Constants.USER_ALREADY_EXISTS, e);
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        } finally {
            lock.unlock();
        }
    }
}
