package tests;

import dbConnection.JDBCConnection;
import org.junit.jupiter.api.*;

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
    @DisplayName("Проверка получения информации о студенте")
    void testFirstUserData() throws SQLException {
        String query = "SELECT FirstName, LastName from students where Id = 1;";
        ResultSet resultSet = JDBCConnection.selectDataFromDB(query);
        resultSet.first();
        Assertions.assertEquals("Andrei", resultSet.getString("FirstName"));
        Assertions.assertEquals("Kuturmazov", resultSet.getString("LastName"));
    }
}
