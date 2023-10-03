package org.example.dao;

import org.example.model.Question;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.After;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionDAOTest {

    private Connection connection;
    private QuestionDAO questionDAO;

    @BeforeEach
    void setUp() {

        String jdbcUrl = "jdbc:mysql://localhost:3306/quiz";
        String username = "root";
        String password = "0000";


        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            questionDAO = new QuestionDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        // Close connection after each test
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSaveQuestion() {

        Question question = new Question();
        question.setTopicId(1);
        question.setDifficulty(2);
        question.setContent("Sample question");

        questionDAO.saveQuestion(question);

        // Check if the question was successfully saved
        assertNotNull(question.getQuestionId());

        // Get the question according to ID and check if it corresponds with saved one.
        Question retrievedQuestion = questionDAO.getQuestionById(question.getQuestionId());
        assertEquals(question.getContent(), retrievedQuestion.getContent());
    }

    @Test
    void testUpdateQuestion() {

        Question question = new Question();
        question.setTopicId(1);
        question.setDifficulty(2);
        question.setContent("Sample question");
        questionDAO.saveQuestion(question);

        // Change data in the question and save it
        question.setTopicId(3);
        question.setDifficulty(4);
        question.setContent("Updated question");
        questionDAO.updateQuestion(question);

        //Get updated question and check if it was updted
        Question updatedQuestion = questionDAO.getQuestionById(question.getQuestionId());
        assertEquals(question.getTopicId(), updatedQuestion.getTopicId());
        assertEquals(question.getDifficulty(), updatedQuestion.getDifficulty());
        assertEquals(question.getContent(), updatedQuestion.getContent());
    }

    @Test
    void testDeleteQuestion() {

        Question question = new Question();
        question.setTopicId(1);
        question.setDifficulty(2);
        question.setContent("Sample question");
        questionDAO.saveQuestion(question);


        questionDAO.deleteQuestion(question.getQuestionId());

        // Check if the deleted question still exists
        Question deletedQuestion = questionDAO.getQuestionById(question.getQuestionId());
        assertNull(deletedQuestion);
    }

    @Test
    void testSearchQuestionsByTopic() {

        Question question1 = new Question();
        question1.setTopicId(1);
        question1.setDifficulty(2);
        question1.setContent("Question 1");
        questionDAO.saveQuestion(question1);

        Question question2 = new Question();
        question2.setTopicId(2);
        question2.setDifficulty(3);
        question2.setContent("Question 2");
        questionDAO.saveQuestion(question2);

        Question question3 = new Question();
        question3.setTopicId(1);
        question3.setDifficulty(4);
        question3.setContent("Question 3");
        questionDAO.saveQuestion(question3);

        // Searching questions by topic nr "1"
        List<Question> questions = questionDAO.searchQuestionsByTopic(1);

        // Check if in the list there are 2 questions with topic nr. 1
        assertEquals(2, questions.size());
    }

    @Test
    void testSearchQuestionsByContent() {

        Question question1 = new Question();
        question1.setTopicId(1);
        question1.setDifficulty(2);
        question1.setContent("Sample question 1");
        questionDAO.saveQuestion(question1);

        Question question2 = new Question();
        question2.setTopicId(2);
        question2.setDifficulty(3);
        question2.setContent("Another question");
        questionDAO.saveQuestion(question2);

        Question question3 = new Question();
        question3.setTopicId(1);
        question3.setDifficulty(4);
        question3.setContent("Sample question 2");
        questionDAO.saveQuestion(question3);

        // Searching questions with content "Sample question"
        List<Question> questions = questionDAO.searchQuestionsByContent("Sample question");


        // Checking if in the list there are 2 questions with this content
        assertEquals(2, questions.size());
    }
}

