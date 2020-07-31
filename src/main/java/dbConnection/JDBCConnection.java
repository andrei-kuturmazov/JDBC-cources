package dbConnection;

import utils.Log;
import utils.Property;

import java.sql.*;

public class JDBCConnection {

    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;
    private static final String url = Property.getProperty("url");
    private static final String userName = Property.getProperty("userName");
    private static final String password = Property.getProperty("password");

    public static Connection connectToDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            Log.error("Driver not found");
            Log.error(e.getMessage());
        }
        Log.info("Successfully connected to DB");
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            Log.error("DB connection failed");
            Log.error(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                Log.info("Connection to DB closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }

        if (statement != null) {
            try {
                statement.close();
                Log.info("Statement closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
                Log.info("ResultSet closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }
    }

    public static void createTable(String query) {
        try {
            statement = connectToDb().prepareStatement(query);
            Log.info(String.format("Following request sent: *** %s ***", query));
            statement.executeUpdate(query);
            Log.info("Table successfully created");
        } catch (SQLException e) {
            Log.error("Table creation failed");
            Log.error(e.getMessage());
        }
    }

    public static ResultSet selectDataFromDB(String query) {
        try {
            statement = connectToDb().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Log.info(String.format("Following request sent: *** %s ***", query));
            resultSet = statement.executeQuery(query);
            Log.info("Data from db retrieved");
        } catch (SQLException e) {
            Log.error(e.getMessage());
        }
        return resultSet;
    }

    public static void insertDataToDB(String query) {
        try {
            statement = connectToDb().createStatement();
            Log.info(String.format("Following request sent: *** %s ***", query));
            statement.executeUpdate(query);
            Log.info("Data successfully added");
        } catch (SQLException e) {
            Log.error("Error during data insert occurs");
            Log.error(e.getMessage());
        }
    }

    public static void updateDataInsideDB(String query) {
        try {
            statement = connectToDb().createStatement();
            Log.info(String.format("Following request sent: *** %s ***", query));
            statement.executeUpdate(query);
            Log.info("Data successfully updated");
        } catch (SQLException e) {
            Log.error("Error during data update occurs");
            Log.error(e.getMessage());
        }
    }

    public static void deleteDataFromDb(String query) {
        try {
            statement = connectToDb().createStatement();
            Log.info(String.format("Following request sent: *** %s ***", query));
            statement.executeUpdate(query);
            Log.info("Data successfully deleted");
        } catch (SQLException e) {
            Log.error("Error during data deleting occurs");
            Log.error(e.getMessage());
        }
    }

    public static void deleteTableFromDb(String query) {
        try {
            statement = connectToDb().createStatement();
            Log.info(String.format("Following request sent: *** %s ***", query));
            statement.executeUpdate(query);
            Log.info("Table successfully deleted");
        } catch (SQLException e) {
            Log.error("Error during table deleting occurs");
            Log.error(e.getMessage());
        }
    }
}
