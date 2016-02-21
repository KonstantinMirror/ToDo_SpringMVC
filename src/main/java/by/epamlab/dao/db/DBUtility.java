package by.epamlab.dao.db;

import org.springframework.jdbc.core.JdbcTemplate;


public class DBUtility {
    private static final String ID_QUERY = "SELECT LAST_INSERT_ID()";

    public static Long getID(JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(ID_QUERY, Long.class);
    }
}
