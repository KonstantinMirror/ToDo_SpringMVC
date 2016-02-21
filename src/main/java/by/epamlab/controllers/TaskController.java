package by.epamlab.controllers;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.exceptions.DAOException;
import by.epamlab.exceptions.FileSystemException;
import by.epamlab.exceptions.ValidationException;
import by.epamlab.interfaces.ITaskManager;
import by.epamlab.logic.Action;
import by.epamlab.logic.Location;
import by.epamlab.logic.TaskFile;
import by.epamlab.resources.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    ITaskManager taskManager;

    @RequestMapping(value = "/action", method = RequestMethod.POST)
    public String action(HttpServletRequest request, HttpSession session) {
        String url = (String) session.getAttribute(Constants.LOCATION);
        String urlBack = Constants.URL_TASK + url;
        User user = (User) session.getAttribute(Constants.USER);
        String[] idTasks = request.getParameterValues(Constants.TASK);
        if (idTasks == null) {
            return "forward:" + urlBack;
        }
        long[] id = toLong(idTasks);
        Action action = Action.valueOf(request.getParameter("button").toUpperCase());
        action.doAction(taskManager, user, id);
        return "redirect:" + urlBack;
    }

    @RequestMapping(value = "/edit/*")
    public String edit(HttpServletRequest request) {
        final long idTask = Long.parseLong(getTargetName(request));
        User user = getUser(request);
        Task task = taskManager.getTask(user, idTask);
        request.setAttribute(Constants.TASK, task);
        request.setAttribute(Constants.EDIT, true);
        return "new_task";
    }

    @RequestMapping(value = "/file/*", method = RequestMethod.GET)
    public void file(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);
        try (FileInputStream in = new FileInputStream(TaskFile.getFileSavePath(user, getTargetName(request)));
             ServletOutputStream out = response.getOutputStream()) {
            org.apache.commons.io.IOUtils.copy(in, out);
        } catch (IOException e) {
            throw new FileSystemException();
        }
    }

    @RequestMapping(value = "/new")
    public String newTask() {
        return "new_task";
    }

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public String update(@RequestParam(value = "file") MultipartFile file,
                         @RequestParam(value = "create", required = true) String createDate,
                         @RequestParam(value = "execute", required = true) String executeDate,
                         @RequestParam(value = "description", required = true) String description,
                         @RequestParam("edit") String edit,
                         @RequestParam("idTask") String idTask,
                         HttpSession session,
                         HttpServletRequest request) {

        String url = session.getAttribute(Constants.LOCATION).toString();
        User user = (User) session.getAttribute(Constants.USER);
        try {
            if (edit != null && Boolean.parseBoolean(edit)) {
                taskManager.changeTask(user, description, executeDate, idTask, file);

            } else {
                taskManager.createTask(user, createDate, executeDate, description, file);
            }
            return "redirect:" + Constants.URL_TASK + url;
        } catch (DAOException | ValidationException e) {
            request.setAttribute(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return "new_task";
    }

    @RequestMapping(value = {"/today", "/tomorrow",
            "/some", "/all", "/fixed", "/remove",}, method = RequestMethod.GET)
    public String page(HttpServletRequest request) {
        Location location = getTargetLocation(request);
        List<Task> tasks = location.initPage(request, taskManager);
        request.setAttribute(Constants.TASKS, tasks);
        return "tasks";
    }

    private long[] toLong(String... id) {
        long[] idTask = new long[id.length];
        for (int counter = 0; counter < id.length; counter++) {
            idTask[counter] = Long.valueOf(id[counter]);
        }
        return idTask;
    }

    private User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(Constants.USER);
    }

    private Location getTargetLocation(HttpServletRequest request) {
        return Location.valueOf(getTargetName(request).toUpperCase());
    }

    private String getTargetName(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int index = uri.lastIndexOf(Constants.URL_DELIMITER) + 1;
        return uri.substring(index);
    }
}