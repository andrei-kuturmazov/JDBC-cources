package tests;

import dbConnection.JDBCConnection;
import org.junit.jupiter.api.*;

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
        String query = "CREATE TABLE if not exists students ("
                +    "Id INT (5) NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                +    "age INT,"
                +    "FirstName varchar(20) NOT NULL,"
                +    "LastName varchar(20) NOT NULL)";
        JDBCConnection.createTable(query);
    }
}
