package org.example;

import org.example.dao.QuestionDAO;
import org.example.dao.ResponseDAO;
import org.example.dao.TopicDAO;
import org.example.model.Question;
import org.example.model.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {

            QuestionDAO questionDAO = new QuestionDAO(connection);

            ResponseDAO responseDAO = new ResponseDAO(connection);

            TopicDAO topicDAO = new TopicDAO(connection);

            List<Question> questionsToInsert = new ArrayList<>();

            Question question1 = new Question();
            question1.setTopicId(1);
            question1.setDifficulty(2);
            question1.setContent("Sample question 1");
            questionsToInsert.add(question1);

            Question question2 = new Question();
            question2.setTopicId(1);
            question2.setDifficulty(3);
            question2.setContent("Sample question 2");
            questionsToInsert.add(question2);

            // Inserting data into the DB
            for (Question question : questionsToInsert) {
                questionDAO.saveQuestion(question);
            }

            Question questionToUpdate = new Question();
            questionToUpdate.setQuestionId(7); // Question ID that need to be updated
            questionToUpdate.setTopicId(4); // New value for topic_id
            questionToUpdate.setDifficulty(5); // New value for difficulty
            questionToUpdate.setContent("Updated question content"); // New updated question content

            // call update method
            questionDAO.updateQuestion(questionToUpdate);

            int questionIdToDelete = 15;  // Question id to delete

            questionDAO.deleteQuestion(questionIdToDelete);


            List<Question> questionByTopic = questionDAO.searchQuestionsByTopic(1);

            for (Question question : questionByTopic) {
                System.out.println("Found question by topic: " + question.getContent());
            }


            List<Question> questionsByContent = questionDAO.searchQuestionsByContent("Sample question 2");

            for (Question question : questionsByContent) {
                System.out.println("Found question by content: " + question.getContent());
            }

            int questionIdToRetrieve = 11; // Question ID That would like to retrieve
            Question retrievedQuestion = questionDAO.getQuestionById(questionIdToRetrieve);

            if (retrievedQuestion != null) {
                System.out.println("Retrieved question: " + retrievedQuestion.getContent());
            } else {
                System.out.println("Question not found.");
            }


            Response response = new Response();
            response.setQuestionId(question1.getQuestionId());
            response.setText("Sample response text");
            response.setCorrect(true);

            responseDAO.saveResponse(response);


            Response response1 = new Response();
            response1.setQuestionId(question2.getQuestionId());
            response1.setText("Sample response text 1");
            response1.setCorrect(false);

            responseDAO.saveResponse(response1);


            topicDAO.insertTopic("history");
            topicDAO.insertTopic("music");
            topicDAO.insertTopic("literature");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

