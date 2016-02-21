package by.epamlab.logic;

import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import by.epamlab.interfaces.ITaskManager;
import by.epamlab.resources.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public enum Location {
    TODAY {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getTasksForDayAndPrevious(configSession(request),
                    LogicDate.today(), Constants.Status.INITIALIZED);
        }
    }, TOMORROW {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getTasksForDay(configSession(request),
                    LogicDate.tomorrow(), Constants.Status.INITIALIZED);
        }
    }, FIXED {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getTasksFromStatus(configSession(request), Constants.Status.FIXED);
        }
    }, SOME {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getFutureTask(configSession(request),
                    LogicDate.tomorrow(), Constants.Status.INITIALIZED);
        }
    }, REMOVE {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getTasksFromStatus(configSession(request), Constants.Status.REMOVED);
        }
    }, ALL {
        @Override
        public List<Task> initPage(HttpServletRequest request, ITaskManager taskManager) {
            return taskManager.getAllTasks(configSession(request));
        }
    };

    public abstract List<Task> initPage(HttpServletRequest request,ITaskManager taskManager);

    protected User configSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.LOCATION, toLowerString());
        return (User) session.getAttribute(Constants.USER);
    }

    public String toLowerString() {
        return this.name().toLowerCase();
    }
}
