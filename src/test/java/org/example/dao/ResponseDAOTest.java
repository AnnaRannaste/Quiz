package org.example.dao;

import org.example.model.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseDAOTest {

    private Connection connection;
    private ResponseDAO responseDAO;

    @BeforeEach
    void setUp() {

        String jdbcUrl = "jdbc:mysql://localhost:3306/quiz";
        String username = "root";
        String password = "0000";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            responseDAO = new ResponseDAO(connection);
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
    void testSaveResponse() {

        Response response = new Response();
        response.setQuestionId(1);
        response.setText("Sample response");
        response.setCorrect(true);

        responseDAO.saveResponse(response);

        // Checking if the response was saved
        assertNotNull(response.getResponseId());

        // Get the response and check by ID  if it is correspond with saved one
        Response retrievedResponse = responseDAO.getResponseById(response.getResponseId());
        assertEquals(response.getText(), retrievedResponse.getText());
    }

    @Test
    void testUpdateResponse() {

        Response response = new Response();
        response.setQuestionId(1);
        response.setText("Sample response");
        response.setCorrect(true);
        responseDAO.saveResponse(response);

        // Updating data in the response
        response.setQuestionId(2);
        response.setText("Updated response");
        response.setCorrect(false);
        responseDAO.updateResponse(response);

        // Checking if the data were updated
        Response updatedResponse = responseDAO.getResponseById(response.getResponseId());
        assertEquals(response.getQuestionId(), updatedResponse.getQuestionId());
        assertEquals(response.getText(), updatedResponse.getText());
        assertEquals(response.isCorrect(), updatedResponse.isCorrect());
    }

    @Test
    void testDeleteResponse() {

        Response response = new Response();
        response.setQuestionId(1);
        response.setText("Sample response");
        response.setCorrect(true);
        responseDAO.saveResponse(response);


        responseDAO.deleteResponse(response.getResponseId());


        Response deletedResponse = responseDAO.getResponseById(response.getResponseId());
        assertNull(deletedResponse);
    }
}
