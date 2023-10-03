package org.example.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TopicDAOTest {

    private Connection connection;
    private TopicDAO topicDAO;

    @BeforeEach
    void setUp() {

        String jdbcUrl = "jdbc:mysql://localhost:3306/quiz";
        String username = "root";
        String password = "0000";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            topicDAO = new TopicDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertTopic() {
        // Insert topic into the db
        String topicName = "Sample Topic1";
        topicDAO.insertTopic(topicName);


    }
}
