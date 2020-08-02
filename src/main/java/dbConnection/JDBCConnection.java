package dbConnection;

import org.apache.commons.dbutils.DbUtils;
import utils.Log;
import utils.Property;

import java.sql.*;

public class JDBCConnection {

    private static PreparedStatement preparedStatement;
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
        DbUtils.closeQuietly(connection, statement, resultSet);
        DbUtils.closeQuietly(preparedStatement);
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

    public static ResultSet selectPreparedDataFromDB(String query) {
        try {
            preparedStatement = connectToDb().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, Property.getProperty("userId"));
            Log.info(String.format("Following request sent: *** %s ***", query));
            resultSet = preparedStatement.executeQuery();
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
