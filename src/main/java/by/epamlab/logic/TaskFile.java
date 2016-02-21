package by.epamlab.logic;


import by.epamlab.bean.Task;
import by.epamlab.bean.User;
import org.apache.log4j.Logger;
import by.epamlab.resources.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskFile {
    public static final Logger LOG = Logger.getLogger(TaskFile.class);

    public static String getFileSavePath(User user, Task task) {
        return Constants.FILE_ROOT_DIRECTORY + File.separator + user.getLogin()
                + File.separator + task.getIdTask();
    }

    public static String getFileSavePath(User user, long idTask) {
        return Constants.FILE_ROOT_DIRECTORY + File.separator + user.getLogin()
                + File.separator + idTask;
    }

    public static String getFileSavePath(User user, String idTask) {
        return Constants.FILE_ROOT_DIRECTORY + File.separator + user.getLogin()
                + File.separator + idTask;
    }


    public static void createFileFolder(User user) throws IOException {
        Files.createDirectory(Paths.get(Constants.FILE_ROOT_DIRECTORY + File.separator
                + user.getLogin()));
    }

    public static void deleteFile(User user, long... idTask) {
        for (long id : idTask) {
            Path path = Paths.get(getFileSavePath(user, id));
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                LOG.error(e);
                e.printStackTrace();
            }
        }
    }
}
