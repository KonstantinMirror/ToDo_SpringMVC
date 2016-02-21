package by.epamlab.dao.db;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.exceptions.DAOException;
import by.epamlab.interfaces.ITaskDAO;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import by.epamlab.resources.Constants;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DBTask implements ITaskDAO {
    public static final Logger LOG = Logger.getLogger(DBUser.class);

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public Task addTask(User user, String description, Constants.Status status,
                        Date creationDate, Date executionDate, String fileName) {
        String ADD_QUERY = "INSERT INTO tasks (creationDate, executionDate, description," +
                "statusTask, fileName, idLogin) \n" +
                "VALUES(?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(ADD_QUERY, creationDate,
                    executionDate, description, status.toString(), fileName, user.getIdUser());
            Long id = DBUtility.getID(jdbcTemplate);
            return new Task(id, description, status, creationDate, executionDate, fileName);
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public void changeTask(User user, String description, Date executionDate, String fileName, long idTask) {
        String CHANGE_QUERY = "UPDATE tasks  SET executionDate = ?, description = ? , fileName = ?" +
                " WHERE idTask = ? AND idLogin = ?";
        try {
            jdbcTemplate.update(CHANGE_QUERY, executionDate, description,
                    fileName, idTask, user.getIdUser());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }

    }

    @Override
    public void changeTask(User user, String description, Date executionDate, long idTask) {
        String CHANGE_QUERY = "UPDATE tasks  SET executionDate = ?, description = ? " +
                " WHERE idTask = ? AND idLogin = ?";
        try {
            jdbcTemplate.update(CHANGE_QUERY, executionDate, description,
                    idTask, user.getIdUser());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }

    }

    @Override
    public void deleteTask(User user, long idTask) {
        String DELETE_QUERY = "DELETE FROM tasks " +
                " WHERE idTask = ? AND idLogin = ?";
        try {
            jdbcTemplate.update(DELETE_QUERY, idTask, user.getIdUser());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public void changeStateTask(User user, Constants.Status status, long idTask) {
        String CHANGE_QUERY = "UPDATE tasks  SET statusTask = ?" +
                " WHERE idTask = ? AND idLogin = ?";
        try {
            jdbcTemplate.update(CHANGE_QUERY, status.toString(),
                    idTask, user.getIdUser());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public List<Task> getTasksForDay(User user, Date day, Constants.Status status) {
        String TASK_FOR_DAY = "SELECT * FROM tasks " +
                "WHERE  executionDate = ?  AND statusTask = ? AND idLogin = ? ";
        try {
            return jdbcTemplate.query(TASK_FOR_DAY,
                    new Object[]{day.toString(), status.toString(), user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }


    @Override
    public List<Task> getTasksFromStatus(User user, Constants.Status status) {
        String REMOVED_TASK = "SELECT * FROM tasks WHERE statusTask = ? AND idLogin = ?";
        try {
            return jdbcTemplate.query(REMOVED_TASK,
                    new Object[]{status.toString(), user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public List<Task> getAllTasks(User user) {
        String All_TASK_QUERY = "SELECT * FROM tasks WHERE idLogin = ?";
        try {
            return jdbcTemplate.query(All_TASK_QUERY, new Object[]{user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }


    @Override
    public List<Task> getTasksForDayAndPrevious(User user, Date day, Constants.Status status) {
        String TASK_FOR_DAY = "SELECT * FROM tasks " +
                "WHERE executionDate <= ? AND statusTask = ? AND idLogin = ?";
        try {
            return jdbcTemplate.query(TASK_FOR_DAY,
                    new Object[]{day, status.toString(), user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public List<Task> getFutureTask(User user, Date notInitLast, Constants.Status status) {
        String TASK_FOR_DAY = "SELECT * FROM tasks " +
                " WHERE  executionDate > ?   AND " +
                " statusTask = ? AND idLogin = ? ";
        try {
            return jdbcTemplate.query(TASK_FOR_DAY,
                    new Object[]{notInitLast, status.toString(), user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    @Override
    public Task getTask(User user, long idTask) {
        String TASK = "SELECT * FROM tasks " +
                " WHERE  idTask = ?   AND " +
                " idLogin = ? ";
        try {
            return jdbcTemplate.queryForObject(TASK,
                    new Object[]{idTask, user.getIdUser()}, new TaskMapper());
        } catch (DataAccessException e) {
            LOG.error(e);
            throw new DAOException(Constants.ERROR_DB, e);
        }
    }

    private final class TaskMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet resultSet, int i) throws SQLException {
            Task task = new Task();
            task.setIdTask(resultSet.getLong("idTask"));
            task.setCreationDate(resultSet.getDate("creationDate"));
            task.setExecutionDate(resultSet.getDate("executionDate"));
            task.setDescription(resultSet.getString("description"));
            task.setStatus(resultSet.getString("statusTask"));
            task.setFileName(resultSet.getString("fileName"));
            return task;
        }
    }

}
