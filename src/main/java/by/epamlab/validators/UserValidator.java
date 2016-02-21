package by.epamlab.validators;

import by.epamlab.exceptions.ValidationException;

public class UserValidator {

    private static final int MAX_LENGTH = 50;
    private static final int MIN_LENGTH_PASSWORD = 3;
    private static final int MIN_LENGTH_LOGIN = 1;

    public static void validate(String login, String password) {
        loginValidate(login);
        passwordValidate(password);

    }

    public static void validate(String login, String password, String email) {
        validate(login, password);
        emailValidate(email);
    }

    private static void loginValidate(String login) {
        if (!login.matches("\\w{" + MIN_LENGTH_LOGIN + "," + MAX_LENGTH + "}")) {
            throw new ValidationException("Login must be between " + MIN_LENGTH_LOGIN +
                    " and " + MAX_LENGTH + " characters");
        }
    }

    private static void passwordValidate(String password) {
        if (!password.matches("\\w{" + MIN_LENGTH_PASSWORD + "," + MAX_LENGTH + "}")) {
            throw new ValidationException("Password must be between " + MIN_LENGTH_PASSWORD +
                    " and " + MAX_LENGTH + " characters");
        }
    }

    private static void emailValidate(String email) {
        if (!(email.matches("\\w+@\\w+") && email.length() <= MAX_LENGTH)) {
            throw new ValidationException("Incorrect email");
        }
    }

}
