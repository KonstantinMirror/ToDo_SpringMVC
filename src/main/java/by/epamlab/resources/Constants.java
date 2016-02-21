package by.epamlab.resources;


public class Constants {
    public static final String USER_ALREADY_EXISTS = "User with the same name already exists";
    public static final String NO_SUCH_USER = "Is no such user";
    public static final String NOT_CORRECT_DATE = "Date is not correct";
    public static final String NOT_CORRECT_FILE_NAME = "File name is not correct";
    public static final String ERROR_DB = "DATABASE ERROR";
    public static final String SOME_FIELDS_IS_EMPTY = "Some fields is empty";
    public static final String NO_FILE = "NO FILE";
    public static final String ERROR_FILE_TASK = "Oops ! something wrong with file task  ";
    public static final String ERROR_CREATE_FILE_DIRECTORY = "Can't create user directory";
    public static final String FILE_NAME_TOO_LONG = "File name is too long";


    public static final String ID_LOGIN = "idLogin";
    public static final String ROLE = "role";
    public static final String EDIT = "edit";
    public static final String LOGIN = "login";
    public static final String USER = "user";
    public static final String TASK = "task";
    public static final String TASKS = "tasks";
    public static final String PASSWORD = "pwd";
    public static final String EMAIL = "email";
    public static final String LOCATION = "location";


    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String PAGE_MAIN = "/index.jsp";
    public static final String URL_DELIMITER = "/";
    public static final String URL_TASK = "/tasks/";


    public static final String FILE_ROOT_DIRECTORY = "d:/ToDo/Files";

    public enum Role {ADMIN, BOSS, VISITOR, USER}

    public enum Status {INITIALIZED, FIXED, REMOVED}
}
