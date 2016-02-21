package by.epamlab.interfaces;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.resources.Constants;

import java.sql.Date;
import java.util.List;


public interface ITaskDAO {

    Task addTask(User user, String description, Constants.Status status,
                 Date creationDate, Date executionDate, String fileName);

    void changeTask(User user, String description, Date executionDate,
                    String fileName, long idTask);

    void changeTask(User user, String description,
                    Date executionDate, long idTask);

    void deleteTask(User user, long idTask);

    void changeStateTask(User user, Constants.Status status, long idTask);

    List<Task> getTasksFromStatus(User user, Constants.Status status);

    List<Task> getTasksForDay(User user, Date day, Constants.Status status);

    List<Task> getTasksForDayAndPrevious(User user, Date day, Constants.Status status);


    List<Task> getFutureTask(User user, Date notInitLast, Constants.Status status);

    List<Task> getAllTasks(User user);

    Task getTask(User user, long idTask);

}
