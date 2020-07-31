package tests;

import dbConnection.JDBCConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import utils.Log;

public class TestInit {
    @BeforeEach
    public void setUp(TestInfo info) {
        Log.info(String.format(" *** The following test started: %s ****", info.getDisplayName()));
    }

    @AfterEach
    public void tearDown(TestInfo info) {
        JDBCConnection.closeConnection();
        Log.info(String.format("*** Test ended: %s ***", info.getDisplayName()));
    }
}
