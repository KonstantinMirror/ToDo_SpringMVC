package by.epamlab.interfaces;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.resources.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;


public interface ITaskManager {
    void createTask(User user, String createDateInput, String executeDateInput,
                    String description, MultipartFile multipartFile);

    void changeTask(User user, String description,
                    String executionDateInput, String idTaskInput, MultipartFile multipartFile);

    void deleteTasks(User user, long... idTasks);

    void deleteTask(User user, long idTask);

    void changeStateTasks(User user, Constants.Status status, long... idTasks);

    void changeStateTask(User user, Constants.Status status, long idTask);

    List<Task> getTasksFromStatus(User user, Constants.Status status);

    List<Task> getTasksForDay(User user, Date day, Constants.Status status);

    List<Task> getTasksForDayAndPrevious(User user, Date day, Constants.Status status);

    List<Task> getFutureTask(User user, Date notInitLast, Constants.Status status);

    List<Task> getAllTasks(User user);

    Task getTask(User user, long idTask);
}
