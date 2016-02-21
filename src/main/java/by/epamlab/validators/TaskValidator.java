package by.epamlab.validators;


import by.epamlab.exceptions.ValidationException;
import by.epamlab.resources.Constants;

public class TaskValidator {

    private static final int MAX_LENGTH_FILE_NAME = 50;

    public static void fileNameValidate(String fileName){
        if (fileName.length()>MAX_LENGTH_FILE_NAME){
            throw new ValidationException(Constants.FILE_NAME_TOO_LONG);
        }else if(fileName.matches("\\w+")){
            throw new ValidationException(Constants.NOT_CORRECT_FILE_NAME);
        }
    }
}
