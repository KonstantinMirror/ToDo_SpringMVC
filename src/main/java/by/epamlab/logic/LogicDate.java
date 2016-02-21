package by.epamlab.logic;

import by.epamlab.exceptions.ValidationException;
import by.epamlab.resources.Constants;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class LogicDate {
    public static Date today() {
        LocalDate date = LocalDate.now();
        return Date.valueOf(date);
    }

    public static Date tomorrow() {
        LocalDate date = LocalDate.now().plusDays(1);
        return Date.valueOf(date);
    }

    public static Date getDate(String currentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = null;
        try {
            parsed = format.parse(currentDate);
            return new Date(parsed.getTime());
        } catch (ParseException e) {
            throw new ValidationException(Constants.NOT_CORRECT_DATE, e);
        }
    }
}
