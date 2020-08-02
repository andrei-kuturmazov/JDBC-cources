package tests;

import dbConnection.JDBCConnection;
import org.junit.jupiter.api.*;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка подключения и отправки запросов к БД users")
public class DbTest extends TestInit {

    @Test
    @Order(1)
    @DisplayName("Test DB connection")
    void testDbConnection() {
        Assertions.assertNotNull(JDBCConnection.connectToDb());
    }

    @Test
    @Order(2)
    @DisplayName("Создание таблицы students в БД")
    void testTableCreation() {
        String query = "CREATE TABLE if not exists students\n" +
                "    (Id INT (5) NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                "    FirstName varchar(20) NOT NULL,\n" +
                "    LastName varchar(20) NOT NULL,\n" +
                "    HomeTown varchar(20),\n" +
                "    Age INT);";
        JDBCConnection.createTable(query);
    }

    @Test
    @Order(3)
    @DisplayName("Проверка добавления данных в таблицу students")
    void testDataAdding() {
        String query = "Insert students(FirstName, LastName, HomeTown, Age) VALUES\n" +
                "('Andrei', 'Kuturmazov', 'Grodno', 31),\n" +
                "('Stepan', 'Petrov', 'Minsk', 33),\n" +
                "('Hanna', 'Kulba', 'Grodno', 18),\n" +
                "('Karyna', 'Karmaza', 'Rymdyni', 18);";
        JDBCConnection.insertDataToDB(query);
    }

    @Test
    @Order(4)
    @DisplayName("Проверка получения информации о первом студенте")
    void testFirstStudentData() throws SQLException {
        String query = "SELECT FirstName, LastName from students where Id = 1;";
        ResultSet resultSet = JDBCConnection.selectDataFromDB(query);
        resultSet.first();
        Assertions.assertEquals("Andrei", resultSet.getString(1));
        Assertions.assertEquals("Kuturmazov", resultSet.getString(2));
    }

    @Test
    @Order(5)
    @DisplayName("Проверка получения информации о последнем студенте через preparedStatement")
    void testLastStudentData() throws SQLException {
        String query = "Select FirstName, LastName from students where Id = ?";
        ResultSet resultSet = JDBCConnection.selectPreparedDataFromDB(query);
        resultSet.first();
        Assertions.assertEquals("Karyna", resultSet.getString("FirstName"));
        Assertions.assertEquals("Karmaza", resultSet.getString("LastName"));
    }

    @Test
    @Order(6)
    @DisplayName("Проверка получения информации о студенте используя RowSet")
    void testStudentInfoUsingRowSet() throws SQLException {
        String query = "Select FirstName, LastName from students where Id = 3";
        RowSet rowSet = JDBCConnection.selectDataUsingRowSet(query);
        rowSet.first();
        Assertions.assertEquals("Hanna", rowSet.getString(1));
        Assertions.assertEquals("Kulba", rowSet.getString(2));
    }

    @Test
    @Order(7)
    @DisplayName("Проверка получения информации о последнем студенте")
    void testLastUserData() throws SQLException {
        String query = "SELECT FirstName, LastName from students;";
        ResultSet resultSet = JDBCConnection.selectDataFromDB(query);
        resultSet.last();
        Assertions.assertEquals("Karyna", resultSet.getString("FirstName"));
        Assertions.assertEquals("Karmaza", resultSet.getString("LastName"));
    }

    @Test
    @Order(8)
    @DisplayName("Проверка обновления информации о студенте")
    void testUserInfoUpdate() throws SQLException {
        String query = "UPDATE students SET FirstName = 'Vasili', LastName = 'Yanushkevich', HomeTown = 'Grodno' where Id = 2;";
        String selectQuery = "SELECT FirstName from students where id =2";
        JDBCConnection.updateDataInsideDB(query);
        ResultSet resultSet = JDBCConnection.selectDataFromDB(selectQuery);
        resultSet.first();
        Assertions.assertEquals("Vasili", resultSet.getString(1));
    }

    @Test
    @Order(9)
    @DisplayName("Проверка создание Представления")
    void testViewCreation() {
        String query = "CREATE VIEW testView as SELECT * from students where FirstName in ('Andrei', 'Vasili');";
        JDBCConnection.createView(query);
    }

    @Test
    @Order(10)
    @DisplayName("Провека удаления пользователя из таблицы")
    void testUserDelete() {
        String query = "Delete from students where Id = 1;";
        JDBCConnection.deleteDataFromDb(query);
    }

    @Test
    @Order(11)
    @DisplayName("Проверка удаления представления")
    void testViewDelete() {
        String query = "Drop view testView";
        JDBCConnection.deleteViewFromDb(query);
    }

    @Test
    @Order(12)
    @DisplayName("Проверка удаления таблицы")
    void testTableDelete() {
        String query = "DROP table students;";
        JDBCConnection.deleteTableFromDb(query);
    }
}
