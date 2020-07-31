package tests;

import dbConnection.JDBCConnection;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка подключения к БД users и отправки запросов")
public class DbTest extends TestInit {

    @Test
    @Order(1)
    @DisplayName("Test DB connection")
    public void testDbConnection() {
        Assertions.assertNotNull(JDBCConnection.connectToDb());
    }
}
