package db;

import exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class Db {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/gui";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "getiba19";

    private static Connection connection;

    public static Connection connect() throws DatabaseException {

        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                throw new DatabaseException();
            } catch (SQLException ex) {
                throw new DatabaseException();
            }

        }
        return connection;
    }
}
