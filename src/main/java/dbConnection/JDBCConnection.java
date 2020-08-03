package dbConnection;

import com.sun.rowset.JdbcRowSetImpl;
import org.apache.commons.dbutils.DbUtils;
import utils.Log;
import utils.Property;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.*;

public class JDBCConnection {

    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static Connection connection;
    private static ResultSet resultSet;
    private static JdbcRowSet rowSet;

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
            connection = DriverManager.getConnection(Property.getProperty("url"),
                    Property.getProperty("userName"), Property.getProperty("password"));
        } catch (SQLException e) {
            Log.error("DB connection failed");
            Log.error(e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        DbUtils.closeQuietly(connection, statement, resultSet);
        DbUtils.closeQuietly(preparedStatement);
        DbUtils.closeQuietly(rowSet);
    }

    public static void createInstance(String query, String instance) throws SQLException {
        statement = connectToDb().prepareStatement(query);
        Log.info(String.format("Following request sent: *** %s ***", query));
        statement.executeUpdate(query);
        Log.info(instance);
    }

    public static void changeInstance(String query, String instance) throws SQLException {
        statement = connectToDb().createStatement();
        Log.info(String.format("Following request sent: *** %s ***", query));
        statement.executeUpdate(query);
        Log.info(instance);
    }

    public static void createTable(String query) {
        try {
            createInstance(query, "Table successfully created");
        } catch (SQLException e) {
            Log.error("Table creation failed");
            Log.error(e.getMessage());
        }
    }

    public static void createView(String query) {
        try {
            createInstance(query, "View successfully created");
        } catch (SQLException e) {
            Log.error("View creation failed");
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

    public static RowSet selectDataUsingRowSet(String query) {
        try {
            rowSet = new JdbcRowSetImpl(connectToDb());
            rowSet.setCommand(query);
            Log.info(String.format("Following request sent: *** %s ***", query));
            rowSet.execute();
        } catch (SQLException e) {
            Log.error(e.getMessage());
        }
        return rowSet;
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
            changeInstance(query, "Data successfully added");
        } catch (SQLException e) {
            Log.error("Error during data insert occurs");
            Log.error(e.getMessage());
        }
    }

    public static void updateDataInsideDB(String query) {
        try {
            changeInstance(query, "Data successfully updated");
        } catch (SQLException e) {
            Log.error("Error during data update occurs");
            Log.error(e.getMessage());
        }
    }

    public static void deleteDataFromDb(String query) {
        try {
            changeInstance(query, "Data successfully deleted");
        } catch (SQLException e) {
            Log.error("Error during data deleting occurs");
            Log.error(e.getMessage());
        }
    }

    public static void deleteTableFromDb(String query) {
        try {
            changeInstance(query, "Table successfully deleted");
        } catch (SQLException e) {
            Log.error("Error during table deleting occurs");
            Log.error(e.getMessage());
        }
    }

    public static void deleteViewFromDb(String query) {
        try {
            changeInstance(query, "View successfully deleted");
        } catch (SQLException e) {
            Log.error("Error during view deleting occurs");
            Log.error(e.getMessage());
        }
    }
}
