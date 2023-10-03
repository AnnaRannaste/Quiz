package org.example.dao;

import org.example.DatabaseConnection;
import org.example.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    private Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveQuestion(Question question) {

        String sql = "INSERT INTO questions (topic_id, difficulty, content) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, question.getTopicId());
            preparedStatement.setInt(2, question.getDifficulty());
            preparedStatement.setString(3, question.getContent());

            // SQL request for inserting
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Question saving failed, no rows were modified");
            }

            // Generated question identification
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    question.setQuestionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Question saving failed, failed to get ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateQuestion(Question question) {
        String sql = "UPDATE questions SET topic_id = ?, difficulty = ?, content = ? WHERE question_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, question.getTopicId());
            preparedStatement.setInt(2, question.getDifficulty());
            preparedStatement.setString(3, question.getContent());
            preparedStatement.setInt(4, question.getQuestionId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Question update failed, no lines were changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteQuestion(int questionId) {
        String sql = "DELETE FROM questions WHERE question_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, questionId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Question deleting failed, no lines were changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Question> searchQuestionsByContent(String content) {
        String sql = "SELECT * FROM questions WHERE content LIKE ?";
        List<Question> questions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + content + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setTopicId(resultSet.getInt("topic_id"));
                    question.setDifficulty(resultSet.getInt("difficulty"));
                    question.setContent(resultSet.getString("content"));

                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    public List<Question> searchQuestionsByTopic(int topicId) {
        String sql = "SELECT * FROM questions WHERE topic_id = ?";
        List<Question> questions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, topicId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setTopicId(resultSet.getInt("topic_id"));
                    question.setDifficulty(resultSet.getInt("difficulty"));
                    question.setContent(resultSet.getString("content"));

                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }


    public Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM questions WHERE question_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, questionId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionId(resultSet.getInt("question_id"));
                    question.setTopicId(resultSet.getInt("topic_id"));
                    question.setDifficulty(resultSet.getInt("difficulty"));
                    question.setContent(resultSet.getString("content"));

                    return question;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
