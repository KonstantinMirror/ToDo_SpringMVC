package by.epamlab.logic;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.exceptions.FileSystemException;
import by.epamlab.interfaces.ITaskDAO;
import by.epamlab.interfaces.ITaskManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import by.epamlab.resources.Constants;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Component
public class TaskManager implements ITaskManager {

    public static final Logger LOG = Logger.getLogger(TaskManager.class);

    @Inject
    ITaskDAO taskDao;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void createTask(User user, String createDateInput, String executeDateInput,
                           String description, MultipartFile file) {
        Constants.Status DEFAULT_STATUS = Constants.Status.INITIALIZED;
        Date createDate = LogicDate.getDate(createDateInput);
        Date executeDate = LogicDate.getDate(executeDateInput);
        String fileName;
        try {
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                Task task = taskDao.addTask(user, description, DEFAULT_STATUS, createDate, executeDate, fileName);
                file.transferTo(new File(TaskFile.getFileSavePath(user, task)));
            } else {
                fileName = Constants.NO_FILE;
                taskDao.addTask(user, description, DEFAULT_STATUS, createDate, executeDate, fileName);
            }
        } catch (IOException | MultipartException e) {
            LOG.error(e);
            throw new FileSystemException(Constants.ERROR_FILE_TASK, e);
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void changeTask(User user, String description, String executionDateInput,
                           String idTaskInput, MultipartFile file) {
        Date executeDate = LogicDate.getDate(executionDateInput);
        long idTask = Long.parseLong(idTaskInput);
        String fileName;
        try {
            if (!file.isEmpty()) {
                fileName = file.getOriginalFilename();
                taskDao.changeTask(user, description, executeDate, fileName, idTask);
                TaskFile.deleteFile(user, idTask);
                file.transferTo(new File(TaskFile.getFileSavePath(user, idTask)));
            } else {
                taskDao.changeTask(user, description, executeDate, idTask);
            }
        } catch (IOException | MultipartException e) {
            LOG.error(e);
            throw new FileSystemException(Constants.ERROR_FILE_TASK, e);
        }
    }

    @Override
    public void deleteTasks(User user, long... idTasks) {
        for (long id : idTasks) {
            deleteTask(user, id);
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void deleteTask(User user, long idTask) {
        taskDao.deleteTask(user, idTask);
        TaskFile.deleteFile(user, idTask);
    }

    @Override
    public void changeStateTasks(User user, Constants.Status status, long... idTasks) {
        for (long id : idTasks) {
            changeStateTask(user, status, id);
        }
    }

    @Override
    public void changeStateTask(User user, Constants.Status status, long idTask) {
        taskDao.changeStateTask(user, status, idTask);
    }

    @Override
    public List<Task> getTasksFromStatus(User user, Constants.Status status) {
        return taskDao.getTasksFromStatus(user, status);
    }

    @Override
    public List<Task> getTasksForDay(User user, Date day, Constants.Status status) {
        return taskDao.getTasksForDay(user, day, status);
    }

    @Override
    public List<Task> getTasksForDayAndPrevious(User user, Date day, Constants.Status status) {
        return taskDao.getTasksForDayAndPrevious(user, day, status);
    }

    @Override
    public List<Task> getFutureTask(User user, Date notInitLast, Constants.Status status) {
        return taskDao.getFutureTask(user, notInitLast, status);
    }

    @Override
    public List<Task> getAllTasks(User user) {
        return taskDao.getAllTasks(user);
    }

    @Override
    public Task getTask(User user, long idTask) {
        return taskDao.getTask(user, idTask);
    }
}
