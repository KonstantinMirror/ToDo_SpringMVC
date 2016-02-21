package by.epamlab.logic;

import by.epamlab.bean.User;
import by.epamlab.interfaces.ITaskManager;
import by.epamlab.resources.Constants;

public enum Action {
    FIX {
        @Override
        public void doAction(ITaskManager taskManager, User user, long[] idTask) {
            taskManager.changeStateTasks(user, Constants.Status.FIXED, idTask);
        }
    }, RESTORE {
        @Override
        public void doAction(ITaskManager taskManager, User user, long[] idTask) {
            taskManager.changeStateTasks(user, Constants.Status.INITIALIZED, idTask);
        }


    }, REMOVE {
        @Override
        public void doAction(ITaskManager taskManager, User user, long[] idTask) {
            taskManager.changeStateTasks(user, Constants.Status.REMOVED, idTask);
        }

    }, DELETE {
        @Override
        public void doAction(ITaskManager taskManager, User user, long[] idTask) {
            taskManager.deleteTasks(user, idTask);
        }

    };

    public abstract void doAction(ITaskManager taskManager, User user, long[] idTask);

    public String toLowerString() {
        return this.name().toLowerCase();
    }
}



